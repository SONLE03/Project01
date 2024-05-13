package com.clothing.OrderService.exception;

import com.clothing.OrderService.constant.APIStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException{

    private final APIStatus apiStatus;
}
