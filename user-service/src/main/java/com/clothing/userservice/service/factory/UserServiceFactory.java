package com.clothing.userservice.service.factory;

import com.clothing.userservice.constant.APIStatus;
import com.clothing.userservice.dto.request.UserRequest;
import com.clothing.userservice.exception.BusinessException;
import com.clothing.userservice.model.Role;
import com.clothing.userservice.model.User;
import com.clothing.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class UserServiceFactory {
    private final UserRepository userRepository;
    protected abstract User createUser(User user, UserRequest userRequest);

    protected abstract User updateUser(User user, UserRequest userRequest);
    protected abstract List<User> getAllUsersByRole(Integer role);

    @Transactional
    public User create(UserRequest userRequest, Role role){
        userRepository.findByEmail(userRequest.getEmail()).ifPresent(user -> {
            throw new BusinessException(APIStatus.EMAIL_ALREADY_EXISTED);
        });
        userRepository.findByPhone(userRequest.getPhoneNumber()).ifPresent(user -> {
            throw new BusinessException(APIStatus.PHONE_ALREADY_EXISTED);
        });
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        User user = User.builder()
                .fullName(userRequest.getFullName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhoneNumber())
                .password(userRequest.getPassword())
                .dateOfBirth(userRequest.getDateOfBirth())
                .role(role)
                .createdAt(time)
                .updatedAt(time)
                .build();
        user.createObject();
        return createUser(user, userRequest);
    }
    @Transactional
    public User update(UUID userId, UserRequest userRequest){
        User user = userRepository.findById(userId).orElseThrow(() ->{
                    throw new BusinessException(APIStatus.USER_NOT_FOUND);
                }
        );
        String oldEmail = user.getEmail();
        String newEmail = userRequest.getEmail();
        String oldPhone = user.getPhone();
        String newPhone = userRequest.getPhoneNumber();
        userRepository.findByEmail(newEmail).ifPresent(u -> {
            if(!oldEmail.equals(newEmail)) {
                throw new BusinessException(APIStatus.EMAIL_ALREADY_EXISTED);
            }
        });
        userRepository.findByPhone(newPhone).ifPresent(u -> {
            if(!oldPhone.equals(newPhone)) {
                throw new BusinessException(APIStatus.PHONE_ALREADY_EXISTED);
            }
        });
        Date currentDate = new Date();
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFullName());
        user.setPhone(userRequest.getPhoneNumber());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setUpdatedAt(new Timestamp(currentDate.getTime()));
        return updateUser(user, userRequest);
    }

    public List<User> getAllUsers(Integer role){
        return userRepository.findByRole(Role.convertIntegerToRole(role))
                .orElse(null);
    }
}
