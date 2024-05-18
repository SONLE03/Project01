package com.clothing.OrderService.client;

import com.clothing.OrderService.dto.response.ProductToOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(name = "product-service", url = "${product-service.url}")
public interface ProductFeignClient {
    @RequestMapping(value = "product-service/api/product/{productId}", method = RequestMethod.GET)
    ProductToOrder getProductToOrder(@PathVariable UUID productId);
}
