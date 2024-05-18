package com.es.productService.dto.response;

import com.es.productService.model.ProductStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String product_Name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("warrantyPeriod")
    private Integer warrantyPeriod;
    @JsonProperty("status")
    private String productStatus;
    public ProductResponse(UUID id, String product_Name, String description, BigDecimal price, Integer quantity, Integer warrantyPeriod, ProductStatus productStatus) {
        this.id = id;
        this.product_Name = product_Name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.productStatus = productStatus.getStatus();
    }
}
