package com.clothing.OrderService.controller;

import com.clothing.OrderService.dto.request.OrderRequest;
import com.clothing.OrderService.model.Order;
import com.clothing.OrderService.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order-service/api")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrder(@PathVariable UUID orderId){
        return  orderService.getOrder(orderId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "order-service", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "order-service")
    @Retry(name = "order-service")
    public CompletableFuture<UUID> createOrder(@RequestBody @Valid OrderRequest orderRequest, HttpServletRequest httpServletRequest) {
        log.info("Create Order");
        return CompletableFuture.supplyAsync(() -> orderService.createOrder(orderRequest, httpServletRequest));
    }
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,HttpServletRequest httpServletRequest, RuntimeException runtimeException) {
        log.info("Cannot Create Order Executing Fallback logic");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, create order after some time!");
    }
    @GetMapping(value = "/{orderId}/status", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamOrderStatus(@PathVariable UUID orderId) {
        return orderService.getOrderStatus(orderId);
    }
}
