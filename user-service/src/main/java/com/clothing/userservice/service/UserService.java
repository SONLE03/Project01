package com.clothing.userservice.service;

import com.clothing.userservice.dto.request.UserRequest;
import com.clothing.userservice.dto.response.UserResponse;
import com.clothing.userservice.model.Role;
import com.clothing.userservice.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsersByRole(Integer role);
    UUID createUser(UserRequest userRequest, Role role);
    void updateUser(UUID userId, UserRequest userRequest);
}
