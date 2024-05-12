package com.clothing.authservice.controller;

import com.clothing.authservice.dto.SigninRequest;
import com.clothing.authservice.dto.TokenResponse;
import com.clothing.authservice.dto.UserRequest;
import com.clothing.authservice.service.AuthService;
import jakarta.validation.Valid;
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
    @PostMapping(value = "logout")
    public ResponseEntity<?> logoutUser() {
        authService.logout();
        return ResponseEntity.ok().body("You've been signed out!");
    }

}
