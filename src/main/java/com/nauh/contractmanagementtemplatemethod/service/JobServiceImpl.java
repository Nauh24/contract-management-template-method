package com.nauh.contractmanagementtemplatemethod.service;


import com.nauh.contractmanagementtemplatemethod.exception.ResourceNotFoundException;
import com.nauh.contractmanagementtemplatemethod.model.Job;
import com.nauh.contractmanagementtemplatemethod.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
    }

    @Override
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }
}