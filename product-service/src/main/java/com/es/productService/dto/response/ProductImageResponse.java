package com.es.productService.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImageResponse {
    private ProductResponse productResponse;
    private List<ImageResponse> imageResponseList;
}
