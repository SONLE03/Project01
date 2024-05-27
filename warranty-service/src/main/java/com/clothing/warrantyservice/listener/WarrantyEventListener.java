package com.clothing.warrantyservice.listener;

import com.clothing.warrantyservice.dto.response.WarrantyEventResponse;
import com.clothing.warrantyservice.event.*;
import com.clothing.warrantyservice.service.WarrantyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Component
public class WarrantyEventListener {
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;
    private final WarrantyService warrantyService;
//    @KafkaListener(topics = "warrantyTopic")
//    public void handleProduct(String jsonWarranty) {
//        try {
//            System.out.println(jsonWarranty);
//            ObjectMapper objectMapper = new ObjectMapper();
//            WarrantyEvent warrantyEvent = objectMapper.readValue(jsonWarranty, WarrantyEvent.class);
//            WarrantyEventResponse warrantyEventResponse = warrantyEvent.getWarrantyEventResponse();
//            warrantyService.createWarranty(warrantyEventResponse.getCustomerResponse(), warrantyEventResponse.getProductItemListToCreateWarranty());
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
    @KafkaListener(topics = "warrantyOrderTopic")
    public void handleAddWarranty(String jsonSaga) {
        try {
            System.out.println(jsonSaga);
            ObjectMapper objectMapper = new ObjectMapper();
            SagaEvent sagaEvent = objectMapper.readValue(jsonSaga, SagaEvent.class);
            warrantyService.createWarranty(sagaEvent.getSagaDTO());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @EventListener
    public void handleSendMail(MailEvent mailEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(mailEvent);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonMail = jsonNode.toString();
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("orderTopic", jsonMail);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
    @EventListener
    public void handleOrderSuccess(OrderSuccessEvent orderSuccessEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(orderSuccessEvent);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonOrderSuccess = jsonNode.toString();
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("orderSuccess", jsonOrderSuccess);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
    @EventListener
    public void handleWarrantyFail(WarrantyFailureEvent warrantyFailureEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(warrantyFailureEvent);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonWarrantyFail = jsonNode.toString();
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("warrantyFail", jsonWarrantyFail);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
}
