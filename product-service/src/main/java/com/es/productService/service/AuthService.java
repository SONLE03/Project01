package com.es.productService.service;

import com.es.productService.client.AuthFeignClient;
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

