package com.clothing.MailService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventResponse {
    private UUID orderId;
    private List<OrderItemResponse> orderList;
    private CustomerResponse customerResponse;
    private Timestamp createdAt;
    private BigDecimal total;
}
