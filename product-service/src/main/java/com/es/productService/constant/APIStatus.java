package com.es.productService.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum APIStatus {
    USER_NOT_FOUND(404, "User Not Found"),
    IMAGE_NOT_FOUND(404, "Image not found"),
    CATEGORY_NOT_FOUND(404, "Category Not Found"),
    PRODUCT_NOT_FOUND(404, "Product Not Found");
    private final int status;
    private final String message;
}
