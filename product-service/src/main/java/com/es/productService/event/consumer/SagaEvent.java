package com.es.productService.event.consumer;

import com.es.productService.dto.SagaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SagaEvent {
    private SagaDTO sagaDTO;
}
