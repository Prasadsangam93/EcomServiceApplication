package com.ecomservice.controller;

import com.ecomservice.entity.Customer;
import com.ecomservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // CREATE
    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        log.info("Customer creation request received: {}", customer);
        Customer savedCustomer = customerService.saveCustomer(customer);
        log.info("Customer saved successfully with ID: {}", savedCustomer.getId());
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.getCustomerById(id);
        if (customerOptional.isPresent()) {
            log.info("Customer found with ID: {}", id);
            return ResponseEntity.ok(customerOptional.get());
        } else {
            log.warn("Customer not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // READ ALL
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            log.warn("No customers found");
            return ResponseEntity.noContent().build();
        }
        log.info("Total customers found: {}", customers.size());
        return ResponseEntity.ok(customers);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
            log.info("Customer updated successfully: {}", id);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            log.error("Update failed for ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            log.info("Customer deleted successfully with ID: {}", id);
            return ResponseEntity.ok("Customer deleted successfully.");
        } catch (RuntimeException e) {
            log.error("Delete failed for ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer not found with ID: " + id);
        }
    }
}
