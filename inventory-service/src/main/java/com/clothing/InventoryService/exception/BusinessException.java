package com.clothing.InventoryService.exception;


import com.clothing.InventoryService.constant.APIStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final APIStatus apiStatus;
}
