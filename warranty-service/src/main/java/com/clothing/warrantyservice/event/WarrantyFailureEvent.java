package com.clothing.warrantyservice.event;

import com.clothing.warrantyservice.dto.response.OrderEventResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class WarrantyFailureEvent extends ApplicationEvent {
    private OrderEventResponse order;
    public WarrantyFailureEvent(Object source, OrderEventResponse order) {
        super(source);
        this.order = order;
    }
}
