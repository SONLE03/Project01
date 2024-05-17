package com.clothing.authservice.client;

import com.clothing.authservice.dto.request.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserFeignClient {
    @RequestMapping(value = "user-service/api/users", method = RequestMethod.POST)
    UUID createUser(@RequestBody UserRequest userRequest);
}
