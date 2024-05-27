package com.clothing.warrantyservice.event;

import com.clothing.warrantyservice.dto.response.OrderEventResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class OrderSuccessEvent extends ApplicationEvent {
    private UUID orderId;
    public OrderSuccessEvent(Object source, UUID orderId) {
        super(source);
        this.orderId = orderId;
    }
}
