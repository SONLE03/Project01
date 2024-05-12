package com.clothing.authservice.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull(message = "Email not null")
    private String email;
    @NotNull(message = "Password not null")
    private String password;
    @NotNull(message = "Full name not null")
    private String fullName;
    @NotNull(message = "Phone number not null")
    private String phoneNumber;
    private Date dateOfBirth;
    private Integer role;
}
