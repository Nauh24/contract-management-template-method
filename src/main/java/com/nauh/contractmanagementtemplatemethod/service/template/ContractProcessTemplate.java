package com.nauh.contractmanagementtemplatemethod.service.template;


import com.nauh.contractmanagementtemplatemethod.dto.ContractDTO;
import com.nauh.contractmanagementtemplatemethod.model.Customer;
import com.nauh.contractmanagementtemplatemethod.model.CustomerContract;
import com.nauh.contractmanagementtemplatemethod.model.Job;
import lombok.extern.slf4j.Slf4j;

/**
 * Template Method Pattern implementation for contract processing
 */
@Slf4j
public abstract class ContractProcessTemplate {

    /**
     * Template method defining the skeleton of contract processing algorithm
     */
    public final CustomerContract processContract(ContractDTO contractDTO) {
        log.info("Starting contract processing");

        // Step 1: Find or create customer
        Customer customer = findOrCreateCustomer(contractDTO);

        // Step 2: Find or create job
        Job job = findOrCreateJob(contractDTO);

        // Step 3: Validate contract data
        validateContractData(contractDTO, customer, job);

        // Step 4: Create contract
        CustomerContract contract = createContract(contractDTO, customer, job);

        // Step 5: Process contract specific details
        processContractSpecifics(contract);

        // Step 6: Save contract
        CustomerContract savedContract = saveContract(contract);

        // Step 7: Perform post-processing
        postProcessContract(savedContract);

        log.info("Contract processing completed");
        return savedContract;
    }

    // Abstract methods to be implemented by concrete subclasses
    protected abstract Customer findOrCreateCustomer(ContractDTO contractDTO);

    protected abstract Job findOrCreateJob(ContractDTO contractDTO);

    protected abstract void validateContractData(ContractDTO contractDTO, Customer customer, Job job);

    protected abstract CustomerContract createContract(ContractDTO contractDTO, Customer customer, Job job);

    // Hook methods with default implementation
    protected void processContractSpecifics(CustomerContract contract) {
        // Default implementation does nothing
        log.info("Processing contract specifics (default implementation)");
    }

    protected abstract CustomerContract saveContract(CustomerContract contract);

    protected void postProcessContract(CustomerContract savedContract) {
        // Default implementation does nothing
        log.info("Post-processing contract (default implementation)");
    }
}
