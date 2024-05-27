package com.clothing.authservice.service;

import com.clothing.authservice.constant.ApiStatus;
import com.clothing.authservice.dto.request.ChangePasswordRequest;
import com.clothing.authservice.dto.request.EmailRequest;
import com.clothing.authservice.dto.request.SigninRequest;
import com.clothing.authservice.dto.response.TokenResponse;
import com.clothing.authservice.dto.request.UserRequest;
import com.clothing.authservice.entity.ForgotPassword;
import com.clothing.authservice.entity.RefreshToken;
import com.clothing.authservice.entity.Role;
import com.clothing.authservice.entity.User;
import com.clothing.authservice.event.SendOtpEvent;
import com.clothing.authservice.exception.BusinessException;
import com.clothing.authservice.exception.ObjectNotFoundException;
import com.clothing.authservice.repository.ForgotPasswordRepository;
import com.clothing.authservice.repository.RefreshTokenRepository;
import com.clothing.authservice.repository.UserRepository;
import com.clothing.authservice.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private void saveTokenToRedis(UUID userId, String accessToken, String refreshToken){
        redisTemplate.opsForHash().put("tokens:" + userId, "access_token", accessToken);
        redisTemplate.opsForHash().put("tokens:" + userId, "refresh_token", refreshToken);
        System.out.println(getValueFromRedis("tokens:" + userId, "access_token"));
        System.out.println(getValueFromRedis("tokens:" + userId, "refresh_token"));
    }
    private void deleteTokensInRedis(UUID userId) {
        redisTemplate.delete("tokens:" + userId);
    }
    private void updateAccessToken(UUID userId, String newAccessToken) {
        if (redisTemplate.opsForHash().hasKey("tokens:" + userId, "access_token")) {
            redisTemplate.opsForHash().put("tokens:" + userId, "access_token", newAccessToken);
            System.out.println(getValueFromRedis("tokens:" + userId, "access_token"));
            System.out.println(getValueFromRedis("tokens:" + userId, "refresh_token"));
        } else {
            throw new RuntimeException("Access token not found for user: " + userId);
        }
    }
    private String getValueFromRedis(String key, String subKey) {
        return (String) redisTemplate.opsForHash().get(key, subKey);
    }
    public TokenResponse authenticate(SigninRequest signinRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signinRequest.getUsername(),
                        signinRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(signinRequest.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException("User not found")
                );
        return this.generateToken(user);
    }

    public TokenResponse generateToken(User user){
        RefreshToken refreshToken = jwtUtil.generateRefreshToken(user.getId());
        String accessToken = jwtUtil.generate(user);
        saveTokenToRedis(user.getId(), accessToken, refreshToken.getToken());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }
    @Transactional
    public User createUser(UserRequest userRequest){
        String password = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(password);
        UUID userId = userService.createUser(userRequest);
        User user = User.builder()
                .id(userId)
                .email(userRequest.getEmail())
                .password(password)
                .role(Role.convertIntegerToRole(userRequest.getRole()))
                .build();
        return userRepository.save(user);
    }
    @Transactional
    public TokenResponse refreshToken(String token){
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ObjectNotFoundException("Refresh token not found"));
        UUID userId = refreshToken.getUser().getId();
        try {
            refreshToken = jwtUtil.verifyExpiration(refreshToken);
            String accessToken = jwtUtil.generate(refreshToken.getUser());
            updateAccessToken(userId, accessToken);
            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken())
                    .build();
        } catch (RuntimeException e) {
            deleteTokensInRedis(userId);
            throw e;
        }
    }

    @Transactional
    public void logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                UUID userId = userRepository.findByEmail(username).orElseThrow(
                        () -> new ObjectNotFoundException("User not found")).getId();
                System.out.println(userId);
                jwtUtil.deleteByUserId(userId);
                deleteTokensInRedis(userId);
            }
        }
    }
    public Integer generateOtp(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
    @Transactional
    public String verifyEmail(String email){
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email"));
        int otp = generateOtp();
        EmailRequest emailRequest = EmailRequest.builder()
                .to(email)
                .text("This is the OTP for your Forgot Password request: " + otp)
                .subject("OTP for Forgot Password request")
                .build();
        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expiryDate(new Date(System.currentTimeMillis() + 8 * 60 * 1000))
                .user(user)
                .build();
        forgotPasswordRepository.save(fp);
        applicationEventPublisher.publishEvent(new SendOtpEvent(this, emailRequest));
        return "Email sent for verification!";
    }
    public String verifyOtp(Integer otp, String email){
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email"));
        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new BusinessException(ApiStatus.OTP_INVALID));
        if(fp.getExpiryDate().before(Date.from(Instant.now()))){
            forgotPasswordRepository.delete(fp);
            throw new BusinessException(ApiStatus.OTP_EXPIRY);
        }
        return "OTP verified!";
    }
    public String changePassword(ChangePasswordRequest changePasswordRequest, String email){
        if(!Objects.equals(changePasswordRequest.getPassword(), changePasswordRequest.getRepeatPassword())){
            throw new BusinessException(ApiStatus.PASSWORD_INCORRECT);
        }
        userRepository.updatePassword(email, passwordEncoder.encode(changePasswordRequest.getPassword()));
        return "Password has been changed!";
    }
    private User getUserLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User currentUser = (User) principal;
            return currentUser;
        } else {
            // Handle the case where the principal is not a User object (e.g., String, UserDetails, etc.)
            return null; // or throw an exception, depending on your requirements
        }
    }
    public UUID getIdLogin() {
        var userLogin = getUserLogin();
        if(userLogin == null) return null;
        return userLogin.getId();
    }

}
