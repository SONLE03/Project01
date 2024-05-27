package com.clothing.OrderService.dto.request;

import com.clothing.OrderService.model.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class OrderRequest {
    @NotNull(message = "Customer name cannot be null")
    private UUID customerId;
    @NotNull(message = "Payment method not null")
    private Integer paymentMethod;
    private String note;
    @NotNull(message = "Order item cannot be null")
    private List<OrderItemRequest> orderItemRequestList;
}
