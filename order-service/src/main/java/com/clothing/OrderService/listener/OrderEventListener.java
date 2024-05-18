package com.clothing.OrderService.listener;

import com.clothing.OrderService.dto.response.event.OrderEventResponse;
import com.clothing.OrderService.event.OrderEvent;
import com.clothing.OrderService.event.ProductEvent;
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

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    @EventListener
    public void handleOrderEvent(ProductEvent productEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(productEvent);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonProduct = jsonNode.toString();

            System.out.println(jsonProduct);
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("productTopic", jsonProduct);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
    @EventListener
    public void handleOrderEvent(OrderEvent orderEventResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(orderEventResponse);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonOrder = jsonNode.toString();

            System.out.println(jsonOrder);

            sendToKafka("orderTopic", jsonOrder);
            sendToKafka("warrantyTopic", jsonOrder);

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }

    private void sendToKafka(String topic, String message) throws InterruptedException, ExecutionException {
        Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
            return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
        }).get();
    }
//    @EventListener
//    public void handleOrderEventToWarranty(OrderEvent orderEventResponse, String topic) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.valueToTree(orderEventResponse);
//            ((ObjectNode) jsonNode).remove("timestamp");
//            String jsonOrder = jsonNode.toString();
//
//            System.out.println(jsonOrder);
//            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
//                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, jsonOrder);
//                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
//            }).get();
//        } catch (InterruptedException | ExecutionException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("Error while sending message to Kafka", e);
//        }
//    }
}
