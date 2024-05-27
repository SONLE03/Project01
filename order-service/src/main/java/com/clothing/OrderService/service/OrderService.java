package com.clothing.OrderService.service;

import com.clothing.OrderService.dto.request.OrderRequest;
import com.clothing.OrderService.model.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrder(UUID orderId);
    String createOrder(OrderRequest orderRequest, HttpServletRequest httpServletRequest);
    void handlePaymentResult(UUID orderId, String paymentResult);
    String rollBackOrder(UUID orderId);
    String orderSuccess(UUID orderId);
}
