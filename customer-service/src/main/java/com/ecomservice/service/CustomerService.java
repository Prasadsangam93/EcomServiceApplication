package com.ecomservice.service;

import com.ecomservice.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(Customer customer);

    Optional<Customer> getCustomerById(Long id);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Long id, Customer customerDetails);

    void deleteCustomer(Long id);
}
