package com.es.productService.controller;

import com.es.productService.constant.APIConstant;
import com.es.productService.dto.request.ProductRequest;
import com.es.productService.dto.response.ProductResponse;
import com.es.productService.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(APIConstant.API)
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping(APIConstant.PRODUCT)
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getDetailProduct(@PathVariable UUID productId) {
        return productService.getDetailProduct(productId);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }
    @PutMapping(APIConstant.PRODUCT)
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable UUID productId,@RequestBody ProductRequest request){
        return productService.updateProduct(productId, request);
    }
}
