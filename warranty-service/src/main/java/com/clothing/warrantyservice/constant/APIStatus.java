package com.clothing.warrantyservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum APIStatus {
    WARRANTY_NOT_FOUND(404, "Warranty Not Found"),
    INSUFFICIENT_PRODUCT_QUANTITY(400,"The quantity of products available must be greater than the quantity you want to buy"),
    WARRANTY_EXPIRED(404, "Warranty has expired"),
    ORDER_NOT_FOUND(404, "Order Not Found");

    private final int status;
    private final String message;
}