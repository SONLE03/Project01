package com.clothing.OrderService.event;

import com.clothing.OrderService.dto.response.event.WarrantyEventResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class WarrantyEvent extends ApplicationEvent {
    private WarrantyEventResponse warrantyEventResponse;
    public WarrantyEvent(Object source, WarrantyEventResponse warrantyEventResponse) {
        super(source);
        this.warrantyEventResponse = warrantyEventResponse;
    }
    public WarrantyEvent(WarrantyEventResponse warrantyEventResponse) {
        super(null);
        this.warrantyEventResponse = warrantyEventResponse;
    }
}
