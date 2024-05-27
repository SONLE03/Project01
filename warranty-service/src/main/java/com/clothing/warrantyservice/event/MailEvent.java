package com.clothing.warrantyservice.event;

import com.clothing.warrantyservice.dto.response.OrderEventResponse;
import com.clothing.warrantyservice.dto.response.OrderItemResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class MailEvent extends ApplicationEvent {
    private OrderEventResponse order;
    public MailEvent(Object source, OrderEventResponse order) {
        super(source);
        this.order = order;
    }
}
