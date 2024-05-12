package com.clothing.customerservice.exception;


import com.clothing.customerservice.constant.APIStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final APIStatus apiStatus;
}
