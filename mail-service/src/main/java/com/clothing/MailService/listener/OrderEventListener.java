package com.clothing.MailService.listener;

import com.clothing.MailService.dto.response.OrderEventResponse;
import com.clothing.MailService.event.OrderEvent;
import com.clothing.MailService.service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderEventListener {
    private final ObservationRegistry observationRegistry;
    private final MailService mailService;
    @KafkaListener(topics = "orderTopic")
    public void handleProduct(String jsonOrder) {
        try {
            System.out.println(jsonOrder);
            ObjectMapper objectMapper = new ObjectMapper();
            OrderEvent orderEvent = objectMapper.readValue(jsonOrder, OrderEvent.class);
            OrderEventResponse order = orderEvent.getOrder();
            mailService.sendOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
