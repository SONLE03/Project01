package com.clothing.userservice.repository;

import com.clothing.userservice.model.Role;
import com.clothing.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<List<User>> findByRole(Role role);
}
