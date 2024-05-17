package com.clothing.InventoryService.client;

import com.clothing.InventoryService.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "product-service", url = "${product-service.url}")
public interface ProductFeignClient {
    @RequestMapping(value = "product-service/api", method = RequestMethod.GET)
    List<ProductResponse> getAllProducts();
}
