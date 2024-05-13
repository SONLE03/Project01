package com.clothing.authservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EmailRequest{
    private String to;
    private String subject;
    private String text;
}
