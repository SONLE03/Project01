package com.clothing.OrderService.client;

import com.clothing.OrderService.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient("product-service")
public interface ProductFeignClient {
    @RequestMapping(value = "product-service/api/{productId}", method = RequestMethod.GET)
    ProductResponse getDetailProduct(@PathVariable UUID productId);
}