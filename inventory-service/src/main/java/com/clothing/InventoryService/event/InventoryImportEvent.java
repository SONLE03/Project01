package com.clothing.InventoryService.event;

import com.clothing.InventoryService.dto.response.ProductEventResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@Setter
public class InventoryImportEvent extends ApplicationEvent {
    private List<ProductEventResponse> productList;
    public InventoryImportEvent(Object source, List<ProductEventResponse> productList) {
        super(source);
        this.productList = productList;
    }
    public InventoryImportEvent(List<ProductEventResponse> productList){
        super(null);
        this.productList = productList;
    }
}
