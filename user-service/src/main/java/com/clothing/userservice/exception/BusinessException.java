package com.clothing.userservice.exception;

import com.clothing.userservice.constant.APIStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final APIStatus apiStatus;
}
