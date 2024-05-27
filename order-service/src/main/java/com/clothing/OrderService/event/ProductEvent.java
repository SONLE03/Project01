package com.clothing.OrderService.event;

import com.clothing.OrderService.dto.response.event.ProductEventResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductEvent extends ApplicationEvent {
    private UUID orderId;
    private HashMap<UUID, List<UUID>> productList;
    private Integer type;
    public ProductEvent(Object source, HashMap<UUID, List<UUID>> productList, UUID orderId) {
        super(source);
        this.productList = productList;
        this.orderId = orderId;
    }
    public ProductEvent(HashMap<UUID, List<UUID>> productList, UUID orderId) {
        super(null);
        this.productList = productList;
        this.orderId = orderId;
    }
}
