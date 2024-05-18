package com.clothing.CategoryService.controller;

import com.clothing.CategoryService.dto.request.CategoryRequest;
import com.clothing.CategoryService.dto.response.CategoryResponse;
import com.clothing.CategoryService.entity.Category;
import com.clothing.CategoryService.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/category-service")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }
    @GetMapping("/{categoryId}")
    public CategoryResponse getCategory(@PathVariable Integer categoryId){
        return categoryService.getCategory(categoryId);
    }

    @PostMapping()
    public String createCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        categoryService.createCategory(categoryRequest);
        return "Category was created successfully";
    }
    @PutMapping("/{categoryId}")
    public String modifyCategory(@PathVariable Integer categoryId, @RequestBody @Valid CategoryRequest categoryRequest){
        categoryService.updateCategory(categoryId, categoryRequest);
        return "Category was modified successfully";
    }
    @DeleteMapping("/{categoryId}")
    public String deleteCategory(@PathVariable Integer categoryId){
        categoryService.deleteCategory(categoryId);
        return "Category was deleted successfully";
    }
}
