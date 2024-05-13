package com.clothing.MailService.event;

import com.clothing.MailService.dto.response.SendOtpResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendOtpEvent {
    private SendOtpResponse email;
}
