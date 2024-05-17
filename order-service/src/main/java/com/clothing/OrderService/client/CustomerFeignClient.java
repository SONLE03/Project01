package com.clothing.OrderService.client;

import com.clothing.OrderService.dto.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "customer-service", url = "${customer-service.url}")
public interface CustomerFeignClient {
    @RequestMapping(value = "customer-service/api/customers/{customerId}", method = RequestMethod.GET)
    CustomerResponse getCustomer(@PathVariable UUID customerId);
}
