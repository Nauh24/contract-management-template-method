package com.nauh.contractmanagementtemplatemethod.controller;


import com.nauh.contractmanagementtemplatemethod.model.CustomerContract;
import com.nauh.contractmanagementtemplatemethod.model.Job;
import com.nauh.contractmanagementtemplatemethod.service.ContractService;
import com.nauh.contractmanagementtemplatemethod.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final ContractService contractService;

    @GetMapping
    public String listJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "jobs/list";
    }

    @GetMapping("/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        List<CustomerContract> contracts = contractService.getContractsByJobId(id);
        
        model.addAttribute("job", job);
        model.addAttribute("contracts", contracts);
        return "jobs/view";
    }
}