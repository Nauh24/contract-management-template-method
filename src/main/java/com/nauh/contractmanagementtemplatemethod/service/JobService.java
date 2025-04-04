package com.nauh.contractmanagementtemplatemethod.service;


import com.nauh.contractmanagementtemplatemethod.model.Job;

import java.util.List;

public interface JobService {
    List<Job> getAllJobs();
    Job getJobById(Long id);
    Job saveJob(Job job);
}