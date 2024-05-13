package com.clothing.authservice.exception;

import com.clothing.authservice.constant.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final ApiStatus apiStatus;
}