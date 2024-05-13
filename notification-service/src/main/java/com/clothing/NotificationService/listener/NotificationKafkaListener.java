package com.clothing.NotificationService.listener;

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

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationKafkaListener {
    private final ObservationRegistry observationRegistry;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(String json) {
        try{
            System.out.println(json);
            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("productTopic", json);
                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
            }).get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
