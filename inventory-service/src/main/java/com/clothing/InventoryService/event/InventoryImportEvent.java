package com.clothing.InventoryService.event;

import com.clothing.InventoryService.dto.response.ImportEventResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class InventoryImportEvent extends ApplicationEvent {
    private List<ImportEventResponse> importEventResponseList;
//    private UUID importId;
    public InventoryImportEvent(Object source, List<ImportEventResponse> importEventResponseList) {
        super(source);
        this.importEventResponseList = importEventResponseList;
    }
    public InventoryImportEvent(List<ImportEventResponse> importEventResponseList){
        super(null);
        this.importEventResponseList = importEventResponseList;
    }
//    public InventoryImportEvent(Object source, UUID importId) {
//        super(source);
//        this.importId = importId;
//    }
//    public InventoryImportEvent(UUID importId){
//        super(importId);
//        this.importId = importId;
//    }
}
