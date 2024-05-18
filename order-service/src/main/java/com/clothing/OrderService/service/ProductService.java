package com.clothing.OrderService.service;

import com.clothing.OrderService.client.ProductFeignClient;
import com.clothing.OrderService.dto.response.ProductToOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductFeignClient productFeignClient;

    public ProductToOrder getProductToOrder(UUID productId){
        return productFeignClient.getProductToOrder(productId);
    }
}
