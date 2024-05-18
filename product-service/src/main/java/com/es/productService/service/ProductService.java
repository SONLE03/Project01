package com.es.productService.service;

import com.es.productService.dto.request.ProductRequest;
import com.es.productService.dto.response.ProductEventResponse;
import com.es.productService.dto.response.ProductImageResponse;
import com.es.productService.dto.response.ProductResponse;
import com.es.productService.dto.response.ProductToOrder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductImageResponse> getAllProducts();
    ProductImageResponse getDetailProduct(UUID productId);
    ProductToOrder getProductToOrder(UUID productId);

    void createProduct(List<MultipartFile> multipartFiles, ProductRequest productRequest) throws IOException;

    void updateProduct(UUID productId, List<MultipartFile> multipartFiles, ProductRequest productRequest) throws IOException;
    void deleteProduct(UUID productId) throws IOException;

    void updateQuantity(List<ProductEventResponse> productList);
}
