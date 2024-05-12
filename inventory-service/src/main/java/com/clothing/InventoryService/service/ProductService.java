package com.clothing.InventoryService.service;

import com.clothing.InventoryService.client.ProductFeignClient;
import com.clothing.InventoryService.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductFeignClient productFeignClient;

    public List<ProductResponse> getAllProducts(){
        return productFeignClient.getAllProducts();
    }
}

