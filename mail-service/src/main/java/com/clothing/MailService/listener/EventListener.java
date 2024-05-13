package com.clothing.MailService.listener;

import com.clothing.MailService.dto.response.OrderEventResponse;
import com.clothing.MailService.dto.response.SendOtpResponse;
import com.clothing.MailService.event.OrderEvent;
import com.clothing.MailService.event.SendOtpEvent;
import com.clothing.MailService.service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventListener {
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
    @KafkaListener(topics = "otpTopic")
    public void handleSendOtp(String jsonOtp) {
        try {
            System.out.println(jsonOtp);
            ObjectMapper objectMapper = new ObjectMapper();
            SendOtpEvent sendOtpEvent = objectMapper.readValue(jsonOtp, SendOtpEvent.class);
            SendOtpResponse sendOtp = sendOtpEvent.getEmail();
            mailService.sendOtp(sendOtp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
