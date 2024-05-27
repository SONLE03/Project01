package com.es.productService.service;

import com.es.productService.dto.SagaDTO;
import com.es.productService.dto.request.ProductRequest;
import com.es.productService.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductImageResponse getDetailProduct(UUID productId);
    ProductToOrder getProductToOrder(UUID productId);
    List<UUID> getProductItem(UUID productId, Integer quantity);

    void createProduct(List<MultipartFile> multipartFiles, ProductRequest productRequest) throws IOException;

    void updateProduct(UUID productId, List<MultipartFile> multipartFiles, ProductRequest productRequest) throws IOException;
    void deleteProduct(UUID productId) throws IOException;

    void updateQuantityToImport(List<ProductEventResponse> productList);
    void updateQuantityToOrder(SagaDTO sagaDTO);
    void rollBackProduct(OrderEventResponse orderEventResponse);
}
