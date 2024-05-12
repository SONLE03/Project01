package com.es.productService.exception;

import com.es.productService.constant.APIStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException{

    private final APIStatus apiStatus;
}