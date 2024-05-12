package com.clothing.InventoryService.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum APIStatus {
    PRODUCT_NOT_FOUND(404, "Product not found"),
    PHONE_ALREADY_EXISTED(400, "Phone already existed");
    private final int status;
    private final String message;
}