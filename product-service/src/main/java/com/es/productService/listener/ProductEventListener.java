package com.es.productService.listener;

import com.es.productService.dto.SagaDTO;
import com.es.productService.dto.response.ProductEventResponse;
import com.es.productService.event.ProductEvent;
import com.es.productService.event.RollBackOrderEvent;
import com.es.productService.event.consumer.SagaEvent;
import com.es.productService.event.consumer.WarrantyFailureEvent;
import com.es.productService.service.ProductService;
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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductEventListener {
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;
    private final ProductService productService;

    @KafkaListener(topics = "productImportTopic")
    public void handleProduct(String json) {
        try {
            System.out.println(json);
            ObjectMapper objectMapper = new ObjectMapper();
            ProductEvent productEvent = objectMapper.readValue(json, ProductEvent.class);
            List<ProductEventResponse> productList = productEvent.getProductList();
            productService.updateQuantityToImport(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @KafkaListener(topics = "productOrderTopic")
    public void handleProductOrder(String json) {
        try {
            System.out.println(json);
            ObjectMapper objectMapper = new ObjectMapper();
            SagaEvent sagaEvent = objectMapper.readValue(json, SagaEvent.class);
            productService.updateQuantityToOrder(sagaEvent.getSagaDTO());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @KafkaListener(topics = "warrantyFail")
    public void handleWarrantyFail(String json) {
        try {
            System.out.println(json);
            ObjectMapper objectMapper = new ObjectMapper();
            WarrantyFailureEvent warrantyFailureEvent = objectMapper.readValue(json, WarrantyFailureEvent.class);
            productService.rollBackProduct(warrantyFailureEvent.getOrder());
        } catch (Exception e) {
            e.printStackTrace();
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
    public void handleUpdateProductOrderFail(RollBackOrderEvent rollBackOrderEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(rollBackOrderEvent);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonRollBack = jsonNode.toString();
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("productOrderTopicFail", jsonRollBack);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
    @EventListener
    public void handleAddWarranty(com.es.productService.event.producer.SagaEvent sagaEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(sagaEvent);
            ((ObjectNode) jsonNode).remove("timestamp");
            String jsonSaga = jsonNode.toString();

            System.out.println(jsonSaga);
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("warrantyOrderTopic", jsonSaga);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sending message to Kafka", e);
        }
    }
}
