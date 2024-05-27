package com.es.productService.event.consumer;

import com.es.productService.dto.response.OrderEventResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarrantyFailureEvent {
    private OrderEventResponse order;
}
