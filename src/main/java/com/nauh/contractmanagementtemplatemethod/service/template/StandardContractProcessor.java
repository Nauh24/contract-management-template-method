package com.nauh.contractmanagementtemplatemethod.service.template;

import com.nauh.contractmanagementtemplatemethod.dto.ContractDTO;
import com.nauh.contractmanagementtemplatemethod.exception.ValidationException;
import com.nauh.contractmanagementtemplatemethod.model.ContractStatus;
import com.nauh.contractmanagementtemplatemethod.model.Customer;
import com.nauh.contractmanagementtemplatemethod.model.CustomerContract;
import com.nauh.contractmanagementtemplatemethod.model.Job;
import com.nauh.contractmanagementtemplatemethod.repository.CustomerContractRepository;
import com.nauh.contractmanagementtemplatemethod.repository.CustomerRepository;
import com.nauh.contractmanagementtemplatemethod.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class StandardContractProcessor extends ContractProcessTemplate {

    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;
    private final CustomerContractRepository contractRepository;

    @Override
    @Transactional
    protected Customer findOrCreateCustomer(ContractDTO contractDTO) {
        log.info("Finding or creating customer: {}", contractDTO.getCustomerName());

        // Try to find customer by name or email
        Optional<Customer> existingCustomer = customerRepository.findByNameOrEmail(
                contractDTO.getCustomerName(),
                contractDTO.getCustomerEmail());

        if (existingCustomer.isPresent()) {
            log.info("Found existing customer: {}", existingCustomer.get().getName());
            return existingCustomer.get();
        }

        // Create new customer if not found
        log.info("Creating new customer: {}", contractDTO.getCustomerName());
        Customer newCustomer = new Customer();
        newCustomer.setName(contractDTO.getCustomerName());
        newCustomer.setPhone(contractDTO.getCustomerPhone());
        newCustomer.setEmail(contractDTO.getCustomerEmail());
        newCustomer.setAddress(contractDTO.getCustomerAddress());
        newCustomer.setTaxCode(contractDTO.getCustomerTaxCode());
        newCustomer.setRepresentative(contractDTO.getCustomerRepresentative());

        return customerRepository.save(newCustomer);
    }

    @Override
    @Transactional
    protected Job findOrCreateJob(ContractDTO contractDTO) {
        log.info("Finding or creating job: {}", contractDTO.getJobTitle());

        // Try to find job by title and location
        Optional<Job> existingJob = jobRepository.findByTitleAndLocation(
                contractDTO.getJobTitle(),
                contractDTO.getJobLocation());

        if (existingJob.isPresent()) {
            log.info("Found existing job: {}", existingJob.get().getTitle());
            return existingJob.get();
        }

        // Create new job if not found
        log.info("Creating new job: {}", contractDTO.getJobTitle());
        Job newJob = new Job();
        newJob.setTitle(contractDTO.getJobTitle());
        newJob.setDescription(contractDTO.getJobDescription());
        newJob.setLocation(contractDTO.getJobLocation());
        newJob.setRequiredWorkers(contractDTO.getRequiredWorkers());
        newJob.setSalary(contractDTO.getSalary());
        newJob.setSpecialRequirements(contractDTO.getSpecialRequirements());

        return jobRepository.save(newJob);
    }

    @Override
    protected void validateContractData(ContractDTO contractDTO, Customer customer, Job job) {
        log.info("Validating contract data");

        if (contractDTO.getCustomerName() == null || contractDTO.getCustomerName().trim().isEmpty()) {
            throw new ValidationException("Customer name is required");
        }

        if (contractDTO.getJobTitle() == null || contractDTO.getJobTitle().trim().isEmpty()) {
            throw new ValidationException("Job title is required");
        }

        if (contractDTO.getRequiredWorkers() <= 0) {
            throw new ValidationException("Required workers must be greater than zero");
        }

        if (contractDTO.getStartDate() == null || contractDTO.getEndDate() == null) {
            throw new ValidationException("Start and end dates are required");
        }

        if (contractDTO.getStartDate().isAfter(contractDTO.getEndDate())) {
            throw new ValidationException("Start date cannot be after end date");
        }

        if (contractDTO.getSalary() <= 0) {
            throw new ValidationException("Salary must be greater than zero");
        }

        if (contractDTO.getJobLocation() == null || contractDTO.getJobLocation().trim().isEmpty()) {
            throw new ValidationException("Job location is required");
        }
    }

    @Override
    protected CustomerContract createContract(ContractDTO contractDTO, Customer customer, Job job) {
        log.info("Creating contract for customer: {} and job: {}", customer.getName(), job.getTitle());

        CustomerContract contract = new CustomerContract();
        contract.setCustomer(customer);
        contract.setJob(job);
        contract.setStartDate(contractDTO.getStartDate());
        contract.setEndDate(contractDTO.getEndDate());
        contract.setWorkingPeriod(calculateWorkingPeriod(contractDTO.getStartDate(), contractDTO.getEndDate()));

        // Set payment information
        contract.setBankName(contractDTO.getBankName());
        contract.setAccountNumber(contractDTO.getAccountNumber());
        contract.setBankBranch(contractDTO.getBankBranch());

        // Set initial status
        contract.setStatus(ContractStatus.SIGNED);

        return contract;
    }

    @Override
    protected void processContractSpecifics(CustomerContract contract) {
        log.info("Processing contract specifics for contract with customer: {} and job: {}",
                contract.getCustomer().getName(), contract.getJob().getTitle());

        // Calculate contract duration in days
        long durationInDays = ChronoUnit.DAYS.between(
                contract.getStartDate(),
                contract.getEndDate());

        log.info("Contract duration: {} days", durationInDays);

        // Additional processing can be added here
    }

    @Override
    protected CustomerContract saveContract(CustomerContract contract) {
        log.info("Saving contract for customer: {} and job: {}",
                contract.getCustomer().getName(), contract.getJob().getTitle());
        return contractRepository.save(contract);
    }

    @Override
    protected void postProcessContract(CustomerContract savedContract) {
        log.info("Post-processing contract ID: {}", savedContract.getId());

        // Send notification, update statistics, etc.
        // This is a hook method that can be overridden by subclasses
    }

    // Helper method to calculate working period string
    private String calculateWorkingPeriod(LocalDate startDate, LocalDate endDate) {
        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        long days = ChronoUnit.DAYS.between(
                startDate.plusMonths(months),
                endDate);

        return String.format("%d months, %d days", months, days);
    }
}