package com.clothing.InventoryService.client;

import com.clothing.InventoryService.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "auth", url = "${auth-service.url}")
public interface AuthFeignClient {
    @RequestMapping(value = "auth", method = RequestMethod.GET)
    UUID getIdLogin();
}
