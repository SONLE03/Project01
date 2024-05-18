package com.clothing.warrantyservice.listener;

import com.clothing.warrantyservice.event.WarrantyInvoiceEvent;
import com.clothing.warrantyservice.service.WarrantyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Component
public class WarrantyEventListener {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;
    private final WarrantyService warrantyService;
//    @KafkaListener(topics = "warrantyTopic")
//    public void handleProduct(String jsonOrder) {
//        try {
//            System.out.println(jsonOrder);
//            ObjectMapper objectMapper = new ObjectMapper();
//            OrderEvent orderEvent = objectMapper.readValue(jsonOrder, OrderEvent.class);
//            OrderEventResponse order = orderEvent.getOrder();
//            warrantyService.createWarranty(order);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @EventListener
    public void handleWarrantyInvoiceEvent(WarrantyInvoiceEvent warrantyInvoice) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(warrantyInvoice);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonWarrantyInvoice = jsonNode.toString();

            System.out.println(jsonWarrantyInvoice);
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("warrantyInvoice", jsonWarrantyInvoice);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
}
