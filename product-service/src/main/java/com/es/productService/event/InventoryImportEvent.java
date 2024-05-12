package com.es.productService.event;

import com.es.productService.dto.response.ImportEventResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryImportEvent {
    private List<ImportEventResponse> importEventResponseList;
}