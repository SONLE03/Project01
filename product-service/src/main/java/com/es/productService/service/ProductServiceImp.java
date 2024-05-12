package com.es.productService.service;

import com.es.productService.constant.APIStatus;
import com.es.productService.dto.request.ProductRequest;
import com.es.productService.dto.response.ImportEventResponse;
import com.es.productService.dto.response.ProductResponse;
import com.es.productService.exception.BusinessException;
import com.es.productService.model.CategoryEntity;
import com.es.productService.model.ProductEntity;
import com.es.productService.model.ProductStatus;
import com.es.productService.repository.CategoryRepository;
import com.es.productService.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @PostConstruct()
    public void createCategory(){
        if(categoryRepository.findAll().isEmpty()){
            List<CategoryEntity> categoryEntities = new ArrayList<>();
            categoryEntities.add(new CategoryEntity("Category 1"));
            categoryEntities.add(new CategoryEntity("Category 2"));
            categoryEntities.add(new CategoryEntity("Category 3"));
            categoryRepository.saveAll(categoryEntities);
        }
    }
    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductEntity> products = productRepository.getAllProducts();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        if(!categoryRepository.existsById(request.getCategory()))
            new BusinessException(APIStatus.CATEGORY_NOT_FOUND);
        ProductEntity product = ProductEntity.builder()
                .product_Name(request.getProduct_Name())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(categoryRepository.getById(request.getCategory()))
                .image(request.getImage())
                .productStatus(request.getStatus())
                .build();
        return modelMapper.map(productRepository.save(product), ProductResponse.class);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        try{
            if(!productRepository.existsById(productId))
                new BusinessException(APIStatus.PRODUCT_NOT_FOUND);
            productRepository.deleteById(productId);
        }catch (Exception ex){
            ProductEntity product = productRepository.getById(productId);
            product.setProductStatus(ProductStatus.DELETED.getValue());
            productRepository.save(product);
        }

    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID productId, ProductRequest request) {
        if(!productRepository.existsById(productId))
            new BusinessException(APIStatus.PRODUCT_NOT_FOUND);
        if(!categoryRepository.existsById(request.getCategory()))
            new BusinessException(APIStatus.CATEGORY_NOT_FOUND);
        ProductEntity product = productRepository.getById(productId);
            product.setProduct_Name(request.getProduct_Name());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setCategory(categoryRepository.getById(request.getCategory()));
            product.setImage(request.getImage());
            product.setProductStatus(request.getStatus());
        productRepository.save(product);
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    @Transactional
    public ProductResponse getDetailProduct(UUID productId) {
        if(!productRepository.existsById(productId))
            new BusinessException(APIStatus.PRODUCT_NOT_FOUND);
        return modelMapper.map(productRepository.getById(productId), ProductResponse.class);
    }
    @Override
    @Transactional
    public void importProduct(List<ImportEventResponse> ImportEventResponse){
        List<ProductEntity> productsToUpdate = new ArrayList<>();

        for (ImportEventResponse importProduct : ImportEventResponse) {
            ProductEntity product = productRepository.findById(importProduct.getProductId())
                    .orElseThrow(() -> new BusinessException(APIStatus.PRODUCT_NOT_FOUND));

            product.setQuantity(importProduct.getQuantity());
            productsToUpdate.add(product);
        }

        if (!productsToUpdate.isEmpty()) {
            productRepository.saveAll(productsToUpdate);
        }
    }
}
