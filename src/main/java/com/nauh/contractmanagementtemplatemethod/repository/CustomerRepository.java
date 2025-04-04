package com.nauh.contractmanagementtemplatemethod.repository;

import com.nauh.contractmanagementtemplatemethod.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByNameOrEmail(String name, String email);
    Optional<Customer> findByName(String name);

    // Thêm phương thức tìm kiếm theo tên với LIKE
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);
}