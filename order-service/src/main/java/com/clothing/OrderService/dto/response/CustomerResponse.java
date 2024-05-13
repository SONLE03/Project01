package com.clothing.OrderService.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CustomerResponse {
    private UUID customerId;
    private String fullName;
    private String phone;
}