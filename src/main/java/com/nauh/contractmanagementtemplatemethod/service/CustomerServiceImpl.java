package com.nauh.contractmanagementtemplatemethod.service;

import com.nauh.contractmanagementtemplatemethod.exception.ResourceNotFoundException;
import com.nauh.contractmanagementtemplatemethod.model.Customer;
import com.nauh.contractmanagementtemplatemethod.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public Optional<Customer> findCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public List<Customer> findCustomersByNameContaining(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}

