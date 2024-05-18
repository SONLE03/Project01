package com.clothing.CategoryService.exception;

import com.clothing.CategoryService.constant.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final ApiStatus apiStatus;
}
