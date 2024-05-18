package com.clothing.warrantyservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private UUID customerId;
    private String fullName;
    private String phone;
    private String email;
}
