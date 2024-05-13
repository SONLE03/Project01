package com.clothing.OrderService.service;

import com.clothing.OrderService.client.CustomerFeignClient;
import com.clothing.OrderService.dto.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerFeignClient customerFeignClient;

    public CustomerResponse getCustomer(UUID customerId){
        return customerFeignClient.getCustomer(customerId);
    }
}
