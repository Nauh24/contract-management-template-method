package com.nauh.contractmanagementtemplatemethod.service;


import com.nauh.contractmanagementtemplatemethod.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Optional<Customer> findCustomerByName(String name);
    Customer saveCustomer(Customer customer);

    List<Customer> findCustomersByNameContaining(String name);
}