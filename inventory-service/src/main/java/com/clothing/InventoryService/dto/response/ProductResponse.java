package com.clothing.InventoryService.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
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
    @JsonProperty("image")
    private String image;
    @JsonProperty("warrantyPeriod")
    private Integer warrantyPeriod;
    @JsonProperty("status")
    private String productStatus;
}
