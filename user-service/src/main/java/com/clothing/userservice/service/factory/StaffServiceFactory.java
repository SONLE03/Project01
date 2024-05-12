package com.clothing.userservice.service.factory;

import com.clothing.userservice.dto.request.UserRequest;
import com.clothing.userservice.model.Staff;
import com.clothing.userservice.model.User;
import com.clothing.userservice.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StaffServiceFactory extends UserServiceFactory{
    public StaffServiceFactory(UserRepository userRepository) {
        super(userRepository);
    }
    @Override
    protected User createUser(User user, UserRequest userRequest) {
        return new Staff(user);
    }

    @Override
    protected User updateUser(User user, UserRequest userRequest) {
        return user;
    }

    @Override
    protected List<User> getAllUsersByRole(Integer role) {
        return getAllUsers(role);
    }
}
