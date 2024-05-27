package com.es.productService.controller;

import com.es.productService.constant.APIConstant;
import com.es.productService.dto.request.ProductRequest;
import com.es.productService.dto.response.ProductImageResponse;
import com.es.productService.dto.response.ProductResponse;
import com.es.productService.dto.response.ProductToOrder;
import com.es.productService.service.ProductService;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product-service/api")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/product/{productId}")
    public ProductToOrder getProductToOrder(@PathVariable UUID productId){
        return productService.getProductToOrder(productId);
    }
    @GetMapping("/productItem/{productId}/{quantity}")
    public List<UUID> getProductItem(@PathVariable UUID productId, @PathVariable Integer quantity){
        return productService.getProductItem(productId, quantity);
    }
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductImageResponse getDetailProduct(@PathVariable UUID productId) {
        return productService.getDetailProduct(productId);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestParam("images") @Nullable List<MultipartFile> image, @Valid @ModelAttribute ProductRequest productRequest) throws IOException {
        productService.createProduct(image, productRequest);
        return "Product was created successfully";
    }
    @PutMapping(APIConstant.PRODUCT_ID)
    @ResponseStatus(HttpStatus.OK)
    public String updateProduct(@PathVariable UUID productId, @RequestParam("images") @Nullable List<MultipartFile> image, @ModelAttribute @Valid ProductRequest productRequest) throws IOException {
        productService.updateProduct(productId, image, productRequest);
        return "Product was modified successfully";
    }
    @DeleteMapping(APIConstant.PRODUCT_ID)
    @ResponseStatus(HttpStatus.OK)
    public String deleteProduct(@PathVariable UUID productId) throws IOException {
        productService.deleteProduct(productId);
        return "Product was deleted successfully";
    }
}
