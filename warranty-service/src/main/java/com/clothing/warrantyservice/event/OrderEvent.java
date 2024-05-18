package com.clothing.warrantyservice.event;


import com.clothing.warrantyservice.dto.response.OrderEventResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderEvent {
    private OrderEventResponse order;
}
