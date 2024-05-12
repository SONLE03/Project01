package com.clothing.InventoryService.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ImportInvoiceRequest {
    @NotNull(message = "Product cannot be null")
    private UUID productId;
    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
    @NotNull(message = "Price cannot be null")
    private BigDecimal price;
    @NotNull(message = "Total cannot be null")
    private BigDecimal total;
}
