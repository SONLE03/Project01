package com.clothing.MailService.dto.response;

import lombok.*;

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
