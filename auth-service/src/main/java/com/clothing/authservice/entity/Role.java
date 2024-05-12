package com.clothing.authservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("admin"),
    SELLER("seller");
    private final String role;
    public static Role convertIntegerToRole(int role) {
        return switch (role) {
            case 0 -> Role.ADMIN;
            case 1 -> Role.SELLER;
            default -> null;
        };
    }
}
