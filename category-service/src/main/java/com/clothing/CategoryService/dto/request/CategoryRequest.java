package com.clothing.CategoryService.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryRequest {
    @NotNull(message = "Category name can not be null")
    private String categoryName;
}
