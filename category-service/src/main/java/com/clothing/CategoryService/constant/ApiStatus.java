package com.clothing.CategoryService.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiStatus {
    CATEGORY_NOT_FOUND(404, "Category Not Found"),
    CATEGORY_ALREADY_EXISTED(400, "Category already existed"),
    OTP_EXPIRY(400, "OTP has expired!"),
    OTP_INVALID(400, "Invalid OTP!");
    private final int status;
    private final String message;
}
