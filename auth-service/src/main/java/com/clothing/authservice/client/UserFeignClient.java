package com.clothing.authservice.client;

import com.clothing.authservice.dto.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient("user-service")
public interface UserFeignClient {
    @RequestMapping(value = "user-service/api/users", method = RequestMethod.POST)
    UUID createUser(@RequestBody UserRequest userRequest);
}
