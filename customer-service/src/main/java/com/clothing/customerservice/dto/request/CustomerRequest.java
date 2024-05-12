package com.clothing.customerservice.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;
@Getter
public class CustomerRequest {
    @NotNull(message = "Customer name cannot be null")
    private String fullName;
    @NotNull(message = "Phone cannot be null")
    private String phone;
    @NotNull(message = "Email cannot be null")
    private String email;
    private String province;
    private String district;
    private String ward;
    private String specificAddress;
}
