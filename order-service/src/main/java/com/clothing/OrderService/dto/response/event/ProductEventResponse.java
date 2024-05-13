package com.clothing.OrderService.dto.response.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductEventResponse {
    private UUID productId;
    private Integer quantity;
}
