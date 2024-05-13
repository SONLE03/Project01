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
import org.springframework.context.ApplicationEventPublisher;
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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

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
        return TokenResponse.builder()
                .accessToken(jwtUtil.generate(user))
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
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new ObjectNotFoundException("Refresh token not found"));
        refreshToken = jwtUtil.verifyExpiration(refreshToken);
        return TokenResponse.builder()
                .accessToken(jwtUtil.generate(refreshToken.getUser()))
                .refreshToken(refreshToken.getToken())
                .build();
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
                jwtUtil.deleteByUserId(userId);
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
}
