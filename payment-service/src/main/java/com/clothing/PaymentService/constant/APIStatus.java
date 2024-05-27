package com.clothing.PaymentService.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum APIStatus {
    CUSTOMER_NOT_FOUND(404, "Customer Not Found"),
    INSUFFICIENT_PRODUCT_QUANTITY(400,"The quantity of products available must be greater than the quantity you want to buy"),
    PRODUCT_NOT_FOUND(404, "Product Not Found"),
    ORDER_NOT_FOUND(404, "Order Not Found");

    private final int status;
    private final String message;
}