package com.clothing.authservice.constant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiStatus {
    PASSWORD_INCORRECT(400, "Incorrect password. Please re-enter password!"),
    OTP_EXPIRY(400, "OTP has expired!"),
    OTP_INVALID(400, "Invalid OTP!");
    private final int status;
    private final String message;
}
