package com.clothing.customerservice.service;

import com.clothing.customerservice.constant.APIStatus;
import com.clothing.customerservice.dto.request.CustomerRequest;
import com.clothing.customerservice.dto.response.CustomerResponse;
import com.clothing.customerservice.exception.BusinessException;
import com.clothing.customerservice.model.Customer;
import com.clothing.customerservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public List<CustomerResponse> getAllCustomer() {
        return customerRepository.getCustomer();
    }

    @Override
    public Customer getCustomer(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new BusinessException(APIStatus.CUSTOMER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void createCustomer(CustomerRequest customerRequest) {
        customerRepository.findByPhone(customerRequest.getPhone()).ifPresent(user -> {
            throw new BusinessException(APIStatus.PHONE_ALREADY_EXISTED);
        });
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        Customer customer = Customer.builder()
                .phone(customerRequest.getPhone())
                .email(customerRequest.getEmail())
                .fullName(customerRequest.getFullName())
                .province(customerRequest.getProvince())
                .ward(customerRequest.getWard())
                .district(customerRequest.getDistrict())
                .specificAddress(customerRequest.getSpecificAddress())
                .createdAt(time)
                .updatedAt(time)
                .build();
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void updateCustomer(UUID customerId, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new BusinessException(APIStatus.CUSTOMER_NOT_FOUND));
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        customer.setPhone(customerRequest.getPhone());
        customer.setEmail(customerRequest.getEmail());
        customer.setFullName(customerRequest.getFullName());
        customer.setProvince(customerRequest.getProvince());
        customer.setWard(customerRequest.getWard());
        customer.setDistrict(customerRequest.getDistrict());
        customer.setSpecificAddress(customerRequest.getSpecificAddress());
        customer.setUpdatedAt(time);
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(UUID customerId) {
        if(!customerRepository.existsById(customerId)){
            throw new BusinessException(APIStatus.CUSTOMER_NOT_FOUND);
        }
        customerRepository.deleteById(customerId);
    }
}
