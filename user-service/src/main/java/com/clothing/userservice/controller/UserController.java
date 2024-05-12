package com.clothing.userservice.controller;

import com.clothing.userservice.dto.request.UserRequest;
import com.clothing.userservice.model.Admin;
import com.clothing.userservice.model.Role;
import com.clothing.userservice.model.User;
import com.clothing.userservice.service.AdminService;
import com.clothing.userservice.service.StaffService;
import com.clothing.userservice.service.UserDetailService;
import com.clothing.userservice.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user-service/api/users")
public class UserController {
    private final UserDetailService userDetailService;
    private final AdminService adminService;
    private final StaffService staffService;
    private final Map<Integer, UserService> roleToServiceMap;

    @PostConstruct
    private void initRoleToServiceMap() {
        roleToServiceMap.put(0, adminService);
        roleToServiceMap.put(1, staffService);
    }
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId){
        return userDetailService.getUserById(userId);
    }
    @GetMapping("/all/{role}")
    public List<User> getAllUsersByRole(@PathVariable Integer role){
        return roleToServiceMap.get(role).getAllUsersByRole(role);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createUser(@RequestBody UserRequest userRequest){
        int userRole = userRequest.getRole();
        UUID userId = roleToServiceMap.get(userRole).createUser(userRequest, Role.convertIntegerToRole(userRole));
        return userId;
    }
}
