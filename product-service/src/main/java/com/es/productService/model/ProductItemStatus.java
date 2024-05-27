package com.es.productService.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductItemStatus {
    INSTOCK("IN STOCK"),
    SOLD("SOLD");
    private final String status;
    public static ProductItemStatus convertIntegerToProductItemStatus(int status) {
        return switch (status) {
            case 0 -> ProductItemStatus.INSTOCK;
            case 1 -> ProductItemStatus.SOLD;
            default -> null;
        };
    }
}
