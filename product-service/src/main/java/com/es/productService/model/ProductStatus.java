package com.es.productService.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductStatus {
    INACTIVE("STOP SELLING"),
    ACTIVE("ACTIVE"),
    DELETED("DELETED");
    private final String status;
    public static ProductStatus convertIntegerToProductStatus(int status) {
        return switch (status) {
            case 0 -> ProductStatus.INACTIVE;
            case 1 -> ProductStatus.ACTIVE;
            case 2 -> ProductStatus.DELETED;
            default -> null;
        };
    }
}
