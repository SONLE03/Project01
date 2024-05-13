package com.clothing.MailService.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendOtpResponse {
    private String to;
    private String subject;
    private String text;
}
