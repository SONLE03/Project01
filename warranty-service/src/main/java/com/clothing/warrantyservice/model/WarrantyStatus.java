package com.clothing.warrantyservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WarrantyStatus {
    EXPIRED("EXPIRED"),
    UNEXPIRED("UNEXPIRED");
    private final String status;
    public static WarrantyStatus convertIntegerToWarrantyStatus(int status) {
        return switch (status) {
            case 0 -> WarrantyStatus.EXPIRED;
            case 1 -> WarrantyStatus.UNEXPIRED;
            default -> null;
        };
    }
}