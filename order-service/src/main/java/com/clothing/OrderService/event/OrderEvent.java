package com.clothing.OrderService.event;

import com.clothing.OrderService.dto.response.event.OrderEventResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class OrderEvent extends ApplicationEvent {
    private OrderEventResponse order;

    public OrderEvent(Object source, OrderEventResponse order) {
        super(source);
        this.order = order;
    }
    public OrderEvent(OrderEventResponse order) {
        super(null);
        this.order = order;
    }
}
