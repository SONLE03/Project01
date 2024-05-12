package com.clothing.userservice.service.impl;

import com.clothing.userservice.constant.APIStatus;
import com.clothing.userservice.exception.BusinessException;
import com.clothing.userservice.model.User;
import com.clothing.userservice.repository.UserRepository;
import com.clothing.userservice.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailService {
    private final UserRepository userRepository;
    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(APIStatus.USER_NOT_FOUND));
    }
}
