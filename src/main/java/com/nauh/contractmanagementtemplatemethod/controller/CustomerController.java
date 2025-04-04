package com.nauh.contractmanagementtemplatemethod.controller;


import com.nauh.contractmanagementtemplatemethod.model.Customer;
import com.nauh.contractmanagementtemplatemethod.model.CustomerContract;
import com.nauh.contractmanagementtemplatemethod.service.ContractService;
import com.nauh.contractmanagementtemplatemethod.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ContractService contractService;

    @GetMapping
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers/list";
    }

    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        List<CustomerContract> contracts = contractService.getContractsByCustomerId(id);
        
        model.addAttribute("customer", customer);
        model.addAttribute("contracts", contracts);
        return "customers/view";
    }
}