package com.es.productService.service;

import com.es.productService.client.CategoryFeignClient;
import com.es.productService.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryFeignClient categoryFeignClient;

    public CategoryResponse getCategory(Integer categoryId){
        return categoryFeignClient.getCategory(categoryId);
    }
}

