package com.clothing.customerservice.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum APIStatus {
    CUSTOMER_NOT_FOUND(404, "Customer not found"),
    PHONE_ALREADY_EXISTED(400, "Phone already existed");
    private final int status;
    private final String message;
}
