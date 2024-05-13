package com.clothing.OrderService.dto.request;

import com.clothing.OrderService.model.OrderItem;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class OrderRequest {
    private UUID customerId;
    private String note;
    private List<OrderItemRequest> orderItemRequestList;
}
