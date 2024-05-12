package com.clothing.customerservice.service;

import com.clothing.customerservice.dto.request.CustomerRequest;
import com.clothing.customerservice.dto.response.CustomerResponse;
import com.clothing.customerservice.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerResponse> getAllCustomer();
    Customer getCustomer(UUID customerId);
    void createCustomer(CustomerRequest customerRequest);
    void updateCustomer(UUID customerId, CustomerRequest customerRequest);
    void deleteCustomer(UUID customerId);
}
