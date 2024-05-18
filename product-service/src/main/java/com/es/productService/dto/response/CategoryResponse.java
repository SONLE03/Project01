package com.es.productService.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("category_name")
    private String name;
}

