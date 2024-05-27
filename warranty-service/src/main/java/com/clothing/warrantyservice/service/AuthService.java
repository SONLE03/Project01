package com.clothing.warrantyservice.service;

import com.clothing.warrantyservice.client.AuthFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthFeignClient authFeignClient;

    public UUID getIdLogin(){
        return authFeignClient.getIdLogin();
    }
}

