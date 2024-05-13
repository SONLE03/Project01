package com.clothing.OrderService.dto.request;

import lombok.Getter;

import java.util.UUID;
@Getter
public class OrderItemRequest {
    private UUID productId;
    private Integer quantity;
}
