package com.es.productService.service;

import com.es.productService.dto.request.ProductRequest;
import com.es.productService.dto.response.ProductEventResponse;
import com.es.productService.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponse> getAllProducts();

    ProductResponse createProduct(ProductRequest request);

    void deleteProduct(UUID productId);

    ProductResponse updateProduct(UUID productId, ProductRequest request);

    ProductResponse getDetailProduct(UUID productId);
    void updateQuantity(List<ProductEventResponse> productList);
}
