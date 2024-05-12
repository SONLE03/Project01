package com.clothing.authservice.service;

import com.clothing.authservice.client.UserFeignClient;
import com.clothing.authservice.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserFeignClient userFeignClient;

    public UUID createUser(UserRequest userRequest){
        return userFeignClient.createUser(userRequest);
    }
}
