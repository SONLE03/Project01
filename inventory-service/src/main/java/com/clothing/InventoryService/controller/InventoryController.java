package com.clothing.InventoryService.controller;

import com.clothing.InventoryService.dto.request.ImportInvoiceRequest;
import com.clothing.InventoryService.service.InventoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/inventory-service/api")
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory-service", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory-service")
    @Retry(name = "inventory-service")
    public CompletableFuture<String> createOrder(@RequestBody @Valid List<ImportInvoiceRequest> request) {
        log.info("Create Import Invoice");
        return CompletableFuture.supplyAsync(() -> inventoryService.createImportInvoice(request));
    }
    public CompletableFuture<String> fallbackMethod(List<ImportInvoiceRequest> request, RuntimeException runtimeException) {
        log.info("Cannot Create Import Invoice Executing Fallback logic");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, create import invoice after some time!");
    }
}
