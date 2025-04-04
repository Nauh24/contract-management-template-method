package com.nauh.contractmanagementtemplatemethod.service;



import com.nauh.contractmanagementtemplatemethod.dto.ContractDTO;
import com.nauh.contractmanagementtemplatemethod.model.CustomerContract;

import java.util.List;

public interface ContractService {
    CustomerContract createContract(ContractDTO contractDTO);
    CustomerContract getContractById(Long id);
    List<CustomerContract> getAllContracts();
    List<CustomerContract> getContractsByCustomerId(Long customerId);
    List<CustomerContract> getContractsByJobId(Long jobId);
}