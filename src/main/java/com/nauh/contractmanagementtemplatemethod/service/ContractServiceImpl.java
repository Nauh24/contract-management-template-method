package com.nauh.contractmanagementtemplatemethod.service;


import com.nauh.contractmanagementtemplatemethod.dto.ContractDTO;
import com.nauh.contractmanagementtemplatemethod.exception.ResourceNotFoundException;
import com.nauh.contractmanagementtemplatemethod.model.CustomerContract;
import com.nauh.contractmanagementtemplatemethod.repository.CustomerContractRepository;
import com.nauh.contractmanagementtemplatemethod.service.template.StandardContractProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final CustomerContractRepository contractRepository;
    private final StandardContractProcessor contractProcessor;

    @Override
    public CustomerContract createContract(ContractDTO contractDTO) {
        // Use the template method pattern to process the contract
        return contractProcessor.processContract(contractDTO);
    }

    @Override
    public CustomerContract getContractById(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));
    }

    @Override
    public List<CustomerContract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public List<CustomerContract> getContractsByCustomerId(Long customerId) {
        return contractRepository.findByCustomerId(customerId);
    }

    @Override
    public List<CustomerContract> getContractsByJobId(Long jobId) {
        return contractRepository.findByJobId(jobId);
    }
}