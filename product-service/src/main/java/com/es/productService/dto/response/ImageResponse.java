package com.es.productService.dto.response;

import com.es.productService.model.ProductEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ImageResponse {
    @JsonProperty("imageId")
    private UUID id;
    @JsonProperty("url")
    private String url;
}
