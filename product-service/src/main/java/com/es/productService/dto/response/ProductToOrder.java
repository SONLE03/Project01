package com.es.productService.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProductToOrder {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String product_Name;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("warrantyPeriod")
    private Integer warrantyPeriod;
}
