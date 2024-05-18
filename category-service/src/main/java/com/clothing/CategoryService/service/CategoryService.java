package com.clothing.CategoryService.service;

import com.clothing.CategoryService.dto.request.CategoryRequest;
import com.clothing.CategoryService.dto.response.CategoryResponse;
import com.clothing.CategoryService.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category>  getCategories();
    CategoryResponse getCategory(Integer categoryId);
    void createCategory(CategoryRequest categoryRequest);
    void updateCategory(Integer categoryId, CategoryRequest categoryRequest);
    void deleteCategory(Integer categoryId);
}
