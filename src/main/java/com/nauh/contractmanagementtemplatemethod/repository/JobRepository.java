package com.nauh.contractmanagementtemplatemethod.repository;

import com.nauh.contractmanagementtemplatemethod.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByTitleAndLocation(String title, String location);
}