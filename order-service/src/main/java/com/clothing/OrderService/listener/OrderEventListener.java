package com.clothing.OrderService.listener;

import com.clothing.OrderService.dto.response.event.OrderEventResponse;
import com.clothing.OrderService.event.*;
import com.clothing.OrderService.service.OrderService;
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

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;
    private final OrderService orderService;
    @EventListener
    public void handleOrderEvent(SagaEvent sagaEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(sagaEvent);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonSaga = jsonNode.toString();

            System.out.println(jsonSaga);
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("productOrderTopic", jsonSaga);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }

//    @EventListener
//    public void handleOrderEvent(ProductEvent productEvent) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.valueToTree(productEvent);
//            ((ObjectNode) jsonNode).remove("timestamp");
//            String jsonProduct = jsonNode.toString();
//
//            System.out.println(jsonProduct);
//            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
//                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("productOrderTopic", jsonProduct);
//                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
//            }).get();
//        } catch (InterruptedException | ExecutionException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("Error while sending message to Kafka", e);
//        }
//    }
    @EventListener
    public void handleOrderEvent(OrderEvent orderEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(orderEvent);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonOrder = jsonNode.toString();

            System.out.println(jsonOrder);

            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("orderTopic", jsonOrder);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }

    @EventListener
    public void handleOrderEventToWarranty(WarrantyEvent warrantyEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(warrantyEvent);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonWarranty = jsonNode.toString();

            System.out.println(jsonWarranty);
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("warrantyTopic", jsonWarranty);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
    // Consumer
    @KafkaListener(topics = "paymentTopic")
    public void handleProduct(String paymentResult) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PaymentEvent paymentEvent = objectMapper.readValue(paymentResult, PaymentEvent.class);
            orderService.handlePaymentResult(paymentEvent.getOrderId(), paymentEvent.getPaymentResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @KafkaListener(topics = "productOrderTopicFail")
    public void handleOrderFail(String updateResult) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RollBackOrderEvent rollBackOrderEvent = objectMapper.readValue(updateResult, RollBackOrderEvent.class);
            System.out.println("ORDER FAILED");
            orderService.rollBackOrder(rollBackOrderEvent.getOrderId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @KafkaListener(topics = "orderSuccess")
    public void handleOrderSuccess(String orderSuccess) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderSuccessEvent orderSuccessEvent = objectMapper.readValue(orderSuccess, OrderSuccessEvent.class);
            System.out.println("ORDER SUCCESS");
            orderService.orderSuccess(orderSuccessEvent.getOrderId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
