package com.nauh.contractmanagementtemplatemethod.controller;

import com.nauh.contractmanagementtemplatemethod.dto.ContractDTO;
import com.nauh.contractmanagementtemplatemethod.model.Customer;
import com.nauh.contractmanagementtemplatemethod.model.CustomerContract;
import com.nauh.contractmanagementtemplatemethod.model.Job;
import com.nauh.contractmanagementtemplatemethod.service.ContractService;
import com.nauh.contractmanagementtemplatemethod.service.CustomerService;
import com.nauh.contractmanagementtemplatemethod.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final CustomerService customerService;
    private final JobService jobService;

    @GetMapping
    public String listContracts(Model model) {
        List<CustomerContract> contracts = contractService.getAllContracts();
        model.addAttribute("contracts", contracts);
        return "contracts/list";
    }

    @GetMapping("/new")
    public String showNewContractForm(Model model) {
        model.addAttribute("contractDTO", new ContractDTO());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("jobs", jobService.getAllJobs());
        return "contracts/new";
    }

    @PostMapping("/new")
    public String createContract(@ModelAttribute ContractDTO contractDTO,
                                 RedirectAttributes redirectAttributes) {
        try {
            CustomerContract contract = contractService.createContract(contractDTO);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Contract created successfully with ID: " + contract.getId());
            return "redirect:/contracts";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error creating contract: " + e.getMessage());
            return "redirect:/contracts/new";
        }
    }

    @GetMapping("/{id}")
    public String viewContract(@PathVariable Long id, Model model) {
        CustomerContract contract = contractService.getContractById(id);
        model.addAttribute("contract", contract);
        return "contracts/view";
    }

    @GetMapping("/search-customer")
    public String searchCustomer(@RequestParam String customerName, Model model) {
        model.addAttribute("searchTerm", customerName);

        if (customerName != null && !customerName.trim().isEmpty()) {
            // Sửa để tìm kiếm với LIKE thay vì exact match
            List<Customer> foundCustomers = customerService.findCustomersByNameContaining(customerName);
            model.addAttribute("foundCustomers", foundCustomers);

            if (!foundCustomers.isEmpty()) {
                model.addAttribute("foundCustomer", foundCustomers.get(0));
            }
        }

        return "contracts/customer-search-result";
    }

    @GetMapping("/new-customer")
    public String showNewCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "contracts/new-customer";
    }

    @PostMapping("/new-customer")
    public String createCustomer(@ModelAttribute Customer customer,
                                 RedirectAttributes redirectAttributes) {
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Customer created successfully: " + savedCustomer.getName());
            return "redirect:/contracts/new";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error creating customer: " + e.getMessage());
            return "redirect:/contracts/new-customer";
        }
    }

    @GetMapping("/new-job")
    public String showNewJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "contracts/new-job";
    }

    @PostMapping("/new-job")
    public String createJob(@ModelAttribute Job job,
                            RedirectAttributes redirectAttributes) {
        try {
            Job savedJob = jobService.saveJob(job);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Job created successfully: " + savedJob.getTitle());
            return "redirect:/contracts/new";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error creating job: " + e.getMessage());
            return "redirect:/contracts/new-job";
        }
    }
}

