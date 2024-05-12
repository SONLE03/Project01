package com.clothing.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninRequest {
    @NotNull(message = "Username not null")
    private String username;
    @NotNull(message = "Password not null")
    private String password;
}
