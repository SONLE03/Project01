package com.clothing.MailService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarrantyInvoiceResponse {
    private UUID warrantyInvoiceId;
    private UUID warrantyId;
    private String customerName;
    private String customerEmail;
    private String productName;
    private String description;
    private BigDecimal price;
}
