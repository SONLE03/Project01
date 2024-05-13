package com.clothing.OrderService.service;

import com.clothing.OrderService.client.ProductFeignClient;
import com.clothing.OrderService.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductFeignClient productFeignClient;

    public ProductResponse getDetailProduct(UUID productId){
        return productFeignClient.getDetailProduct(productId);
    }
}
