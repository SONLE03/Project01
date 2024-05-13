package com.clothing.authservice.controller;

import com.clothing.authservice.dto.request.ChangePasswordRequest;
import com.clothing.authservice.dto.request.SigninRequest;
import com.clothing.authservice.dto.response.TokenResponse;
import com.clothing.authservice.dto.request.UserRequest;
import com.clothing.authservice.service.AuthService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signin(@RequestBody @Valid SigninRequest signinRequest){
        return ResponseEntity.ok(authService.authenticate(signinRequest));
    }
    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestBody @Valid UserRequest userRequest){
        authService.createUser(userRequest);
        return "User was created successfully";
    }
    @PostMapping(value = "/refresh-token/{token}")
    public ResponseEntity<TokenResponse> refreshToken(@PathVariable String token) {
        return ResponseEntity.ok(authService.refreshToken(token));
    }
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logoutUser() {
        authService.logout();
        return ResponseEntity.ok().body("You've been signed out!");
    }
    @PostMapping(value = "/otp/{email}")
    public String sendOtp(@PathVariable String email){
        return authService.verifyEmail(email);
    }
    @PostMapping(value = "/verifyOtp/{email}/{otp}")
    public String verifyOtp(@PathVariable Integer otp, @PathVariable String email){
        return authService.verifyOtp(otp, email);
    }
    @PostMapping(value = "/changePassword/{email}")
    public String changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest, @PathVariable String email){
        return authService.changePassword(changePasswordRequest, email);
    }
}
