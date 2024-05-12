package com.es.productService.listener;

import com.es.productService.dto.response.ImportEventResponse;
import com.es.productService.event.InventoryImportEvent;
import com.es.productService.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImportInvoiceEvent {
    private final ObservationRegistry observationRegistry;
    private final ProductService productService;

    @KafkaListener(topics = "productTopic")
    public void handleProduct(String json) {
        try {
            System.out.println(json);
            ObjectMapper objectMapper = new ObjectMapper();
            InventoryImportEvent inventoryImportEvent = objectMapper.readValue(json, InventoryImportEvent.class);
            List<ImportEventResponse> importEventResponseList = inventoryImportEvent.getImportEventResponseList();
            productService.importProduct(importEventResponseList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
