package com.clothing.OrderService.event;

import com.clothing.OrderService.dto.response.event.ProductEventResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@Setter
public class ProductEvent extends ApplicationEvent {
    private List<ProductEventResponse> productList;
    public ProductEvent(Object source, List<ProductEventResponse> productList) {
        super(source);
        this.productList = productList;
    }
    public ProductEvent(List<ProductEventResponse> productList) {
        super(null);
        this.productList = productList;
    }
}
