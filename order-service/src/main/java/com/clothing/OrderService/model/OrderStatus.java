package com.clothing.OrderService.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("PENDING"),
    CANCELED("CANCELED"),
    COMPLETED("COMPLETED"),
    PAID("PAID");
    private final String orderStatus;
    public static OrderStatus convertIntegerToOrderStatus(int status) {
        return switch (status) {
            case 0 -> OrderStatus.PENDING;
            case 1 -> OrderStatus.CANCELED;
            case 2 -> OrderStatus.COMPLETED;
            case 3 -> OrderStatus.PAID;
            default -> null;
        };
    }
}
