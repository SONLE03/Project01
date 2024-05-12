package com.clothing.customerservice.controller;

import com.clothing.customerservice.dto.request.CustomerRequest;
import com.clothing.customerservice.dto.response.CustomerResponse;
import com.clothing.customerservice.model.Customer;
import com.clothing.customerservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
@RestController
@AllArgsConstructor
@RequestMapping(value = "/customer-service/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping()
    public List<CustomerResponse> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable UUID customerId){
        return customerService.getCustomer(customerId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createCustomer(@RequestBody @Valid CustomerRequest customerRequest){
        customerService.createCustomer(customerRequest);
        return "Customer was created successfully";
    }
    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateCustomer(@PathVariable UUID customerId, @RequestBody @Valid CustomerRequest customerRequest){
        customerService.updateCustomer(customerId, customerRequest);
        return "Customer was modified successfully";
    }
    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable UUID customerId){
        customerService.deleteCustomer(customerId);
        return "Customer was deleted successfully";
    }
}
