package com.nauh.contractmanagementtemplatemethod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {
    // Customer information
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private String customerTaxCode;
    private String customerRepresentative;
    
    // Job information
    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private Integer requiredWorkers;
    private Double salary;
    private String specialRequirements;
    
    // Contract information
    private LocalDate startDate;
    private LocalDate endDate;
    
    // Payment information
    private String bankName;
    private String accountNumber;
    private String bankBranch;
}