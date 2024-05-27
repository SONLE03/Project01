package com.clothing.MailService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private UUID productId;
    private List<UUID> productItemList;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
    private Integer warrantyPeriod;
}
