package com.ecomservice.service;

import com.ecomservice.entity.Address;
import com.ecomservice.entity.Customer;
import com.ecomservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        // Important: link each address to its parent customer
      if(customer.getAddresses() != null){

          for(Address address : customer.getAddresses()){
              address.setCustomer(customer);

          }
      }
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }


    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Optional<Customer> existing = customerRepository.findById(id);

        if (existing.isPresent()) {
            Customer customer = existing.get();
            customer.setName(customerDetails.getName());
            customer.setEmail(customerDetails.getEmail());
            customer.setPhone(customerDetails.getPhone());

            // Handle updated addresses (optional)
            if (customerDetails.getAddresses() != null) {
                for (Address address : customerDetails.getAddresses()) {
                    address.setCustomer(customer); // link each address to parent
                }
                customer.setAddresses(customerDetails.getAddresses()); // replace old list
            }

            return customerRepository.save(customer);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
