package com.clothing.userservice.service;

import com.clothing.userservice.model.User;

import java.util.UUID;

public interface UserDetailService {
    User getUserById(UUID userId);
}
