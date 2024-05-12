package com.es.productService.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductStatus {
    ACTIVE(1, "ACTIVE"),
    INACTIVE(2,"STOP SELLING"),
    DELETED(3, "DELETED");
    private final Integer value;
    private final String productStatus;
}
