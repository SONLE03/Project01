package com.clothing.OrderService.event;

import com.clothing.OrderService.dto.response.ProductEventResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@Setter
public class OrderEvent extends ApplicationEvent {
    private List<ProductEventResponse> productList;
    public OrderEvent(Object source, List<ProductEventResponse> productList) {
        super(source);
        this.productList = productList;
    }
    public OrderEvent(List<ProductEventResponse> productList) {
        super(null);
        this.productList = productList;
    }
}
