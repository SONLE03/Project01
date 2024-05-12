package com.clothing.customerservice.repository;

import com.clothing.customerservice.dto.response.CustomerResponse;
import com.clothing.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByPhone(String phone);
    @Query("SELECT new com.clothing.customerservice.dto.response.CustomerResponse(c.id, c.fullName, c.phone) FROM Customer c")
    List<CustomerResponse> getCustomer();
}
