package com.clothing.authservice.event;

import com.clothing.authservice.dto.request.EmailRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class SendOtpEvent extends ApplicationEvent {
    private EmailRequest email;
    public SendOtpEvent(Object source, EmailRequest email) {
        super(source);
        this.email = email;
    }
}
