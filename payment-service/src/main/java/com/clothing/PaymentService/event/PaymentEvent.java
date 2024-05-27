package com.clothing.PaymentService.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class PaymentEvent extends ApplicationEvent {
    private String paymentResult;
    private UUID orderId;
    public PaymentEvent(Object source, String paymentResult, UUID orderId) {
        super(source);
        this.paymentResult = paymentResult;
        this.orderId = orderId;
    }
}
