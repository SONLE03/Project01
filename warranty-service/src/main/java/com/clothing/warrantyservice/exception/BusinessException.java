package com.clothing.warrantyservice.exception;

import com.clothing.warrantyservice.constant.APIStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException{

    private final APIStatus apiStatus;
}
