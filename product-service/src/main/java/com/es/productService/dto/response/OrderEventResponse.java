package com.es.productService.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEventResponse {
    private UUID orderId;
    private List<OrderItemResponse> orderList;
    private CustomerResponse customerResponse;
    private Timestamp createdAt;
    private BigDecimal total;
}
