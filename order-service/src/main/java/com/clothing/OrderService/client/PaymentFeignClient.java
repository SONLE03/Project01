package com.clothing.OrderService.client;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "payment-service", url = "${payment-service.url}")
public interface PaymentFeignClient {
    @RequestMapping(value = "vnpay/submitOrder", method = RequestMethod.POST)
    String submitOrder(@RequestParam("amount") int orderTotal,
                       @RequestParam("orderInfo") String orderInfo,
                       HttpServletRequest request);
}

