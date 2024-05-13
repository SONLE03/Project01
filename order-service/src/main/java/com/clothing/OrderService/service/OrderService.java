package com.clothing.OrderService.service;

import com.clothing.OrderService.dto.request.OrderRequest;
import com.clothing.OrderService.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrder(UUID orderId);
    String createOrder(OrderRequest orderRequest);
    void updateOrderStatus(UUID orderId, Integer status);
}
