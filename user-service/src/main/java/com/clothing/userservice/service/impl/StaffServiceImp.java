package com.clothing.userservice.service.impl;

import com.clothing.userservice.dto.request.UserRequest;
import com.clothing.userservice.model.Role;
import com.clothing.userservice.model.User;
import com.clothing.userservice.repository.UserRepository;
import com.clothing.userservice.service.StaffService;
import com.clothing.userservice.service.UserService;
import com.clothing.userservice.service.factory.AdminServiceFactory;
import com.clothing.userservice.service.factory.StaffServiceFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
public class StaffServiceImp implements StaffService {
    private final StaffServiceFactory staffServiceFactory;
    private final UserRepository userRepository;
    @Override
    public List<User> getAllUsersByRole(Integer role) {
        return staffServiceFactory.getAllUsers(role);
    }

    @Override
    public UUID createUser(UserRequest userRequest, Role role) {
        User user = userRepository.save(staffServiceFactory.create(userRequest, role));
        return user.getId();
    }

    @Override
    public void updateUser(UUID userId, UserRequest userRequest) {
        userRepository.save(staffServiceFactory.update(userId, userRequest));
    }
}
