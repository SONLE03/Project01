package com.clothing.PaymentService.controller;

import com.clothing.PaymentService.event.PaymentEvent;
import com.clothing.PaymentService.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/vnpay")
@RestController
@AllArgsConstructor
public class VNPayController {
    private final VNPayService vnPayService;
    private final ApplicationEventPublisher applicationEventPublisher;
    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("orderId")UUID orderId, @RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.doPost(orderId, request, orderTotal, orderInfo, baseUrl);
        return vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);
        String paymentResult = paymentStatus == 1 ? "ordersuccess" : "orderfail";

        String orderIdStr = request.getParameter("orderId");
        UUID orderId = UUID.fromString(orderIdStr);

        applicationEventPublisher.publishEvent(new PaymentEvent(this, paymentResult, orderId));
        return paymentResult;
    }
}
