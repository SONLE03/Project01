package com.es.productService.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = "Product name can not be null")
    private String product_Name;
    @NotNull(message = "Category can not be null")
    private Integer category;
    private String description;
    private String image;
    @NotNull(message = "Product price can not be null")
    private BigDecimal price;
    @NotNull(message = "Product quantity can not be null")
    private Integer quantity;
    private Integer warrantyPeriod;
    private Integer status;
}
