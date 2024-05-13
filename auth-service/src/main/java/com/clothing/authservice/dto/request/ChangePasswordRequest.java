package com.clothing.authservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    @NotNull(message = "Password can not be null")
    private String password;
    @NotNull(message = "Repeat password can not be null")
    private String repeatPassword;
}
