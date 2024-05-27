package com.clothing.InventoryService.service;

import com.clothing.InventoryService.client.AuthFeignClient;
import com.clothing.InventoryService.client.ProductFeignClient;
import com.clothing.InventoryService.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthFeignClient authFeignClient;

    public UUID getIdLogin(){
        return authFeignClient.getIdLogin();
    }
}

