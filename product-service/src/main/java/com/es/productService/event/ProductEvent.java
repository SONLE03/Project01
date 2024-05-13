package com.es.productService.event;

import com.es.productService.dto.response.ProductEventResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductEvent {
    private List<ProductEventResponse> productList;
}