package com.nauh.contractmanagementtemplatemethod.repository;

import com.nauh.contractmanagementtemplatemethod.model.CustomerContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerContractRepository extends JpaRepository<CustomerContract, Long> {
    List<CustomerContract> findByCustomerId(Long customerId);
    List<CustomerContract> findByJobId(Long jobId);
}