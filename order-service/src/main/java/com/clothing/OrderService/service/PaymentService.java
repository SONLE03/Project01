package com.clothing.OrderService.service;
import com.clothing.OrderService.client.PaymentFeignClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentFeignClient paymentFeignClient;
    String submitOrder(int orderTotal,
                      String orderInfo,
                       HttpServletRequest request){
        return paymentFeignClient.submitOrder(orderTotal,orderInfo,request);
    }
}
