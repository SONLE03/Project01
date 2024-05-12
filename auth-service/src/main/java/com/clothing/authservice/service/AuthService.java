package com.clothing.authservice.service;

import com.clothing.authservice.dto.SigninRequest;
import com.clothing.authservice.dto.TokenResponse;
import com.clothing.authservice.dto.UserRequest;
import com.clothing.authservice.entity.RefreshToken;
import com.clothing.authservice.entity.Role;
import com.clothing.authservice.entity.User;
import com.clothing.authservice.exception.ObjectNotFoundException;
import com.clothing.authservice.repository.RefreshTokenRepository;
import com.clothing.authservice.repository.UserRepository;
import com.clothing.authservice.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
