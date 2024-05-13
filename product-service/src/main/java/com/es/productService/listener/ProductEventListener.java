package com.es.productService.listener;

import com.es.productService.dto.response.ProductEventResponse;
import com.es.productService.event.ProductEvent;
import com.es.productService.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductEventListener {
    private final ObservationRegistry observationRegistry;
    private final ProductService productService;

    @KafkaListener(topics = "productTopic")
    public void handleProduct(String json) {
        try {
            System.out.println(json);
            ObjectMapper objectMapper = new ObjectMapper();
            ProductEvent productEvent = objectMapper.readValue(json, ProductEvent.class);
            List<ProductEventResponse> productList = productEvent.getProductList();
            productService.updateQuantity(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
