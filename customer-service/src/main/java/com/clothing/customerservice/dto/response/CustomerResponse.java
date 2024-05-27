package com.clothing.customerservice.dto.response;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private UUID customerId;
    private String fullName;
    private String phone;
    private String email;
}
