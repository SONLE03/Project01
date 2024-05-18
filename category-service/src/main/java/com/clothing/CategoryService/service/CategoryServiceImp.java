package com.clothing.CategoryService.service;

import com.clothing.CategoryService.constant.ApiStatus;
import com.clothing.CategoryService.dto.request.CategoryRequest;
import com.clothing.CategoryService.dto.response.CategoryResponse;
import com.clothing.CategoryService.entity.Category;
import com.clothing.CategoryService.exception.BusinessException;
import com.clothing.CategoryService.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryResponse getCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new BusinessException(ApiStatus.CATEGORY_NOT_FOUND));
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        String name = categoryRequest.getCategoryName();
        if(!categoryRepository.findByName(name).isEmpty()){
            throw new BusinessException(ApiStatus.CATEGORY_ALREADY_EXISTED);
        }
        categoryRepository.save(Category.builder().name(name).build());
    }

    @Override
    public void updateCategory(Integer categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new BusinessException(ApiStatus.CATEGORY_NOT_FOUND));
        String name = categoryRequest.getCategoryName();
        if(category.getName() != name && !categoryRepository.findByName(name).isEmpty()){
            throw new BusinessException(ApiStatus.CATEGORY_ALREADY_EXISTED);
        }
        category.setName(name);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        if(!categoryRepository.existsById(categoryId)){
            throw new BusinessException(ApiStatus.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(categoryId);
    }
}
