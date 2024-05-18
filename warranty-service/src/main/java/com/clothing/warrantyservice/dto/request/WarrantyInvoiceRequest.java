package com.clothing.warrantyservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WarrantyInvoiceRequest {
    @NotNull(message = "WarrantyID cannot be empty")
    private UUID warrantyId;
    private String description;
    @NotNull(message = "Price cannot be empty")
    private BigDecimal price;
    @NotNull(message = "Customer email cannot be empty")
    private String customerEmail;
    @NotNull(message = "Customer name cannot be empty")
    private String customerName;
    @NotNull(message = "Product name cannot be empty")
    private String productName;
}
