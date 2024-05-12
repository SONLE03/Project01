package com.clothing.userservice.service.factory;

import com.clothing.userservice.dto.request.UserRequest;
import com.clothing.userservice.model.Admin;
import com.clothing.userservice.model.User;
import com.clothing.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminServiceFactory extends UserServiceFactory {
    public AdminServiceFactory(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    @Transactional
    protected User createUser(User user, UserRequest userRequest) {
        return new Admin(user);
    }

    @Override
    @Transactional
    protected User updateUser(User user, UserRequest userRequest) {
        return user;
    }

    @Override
    protected List<User> getAllUsersByRole(Integer role) {
        return getAllUsers(role);
    }
}