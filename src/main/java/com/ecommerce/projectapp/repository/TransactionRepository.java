package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByOrderId(Long orderId);

    List<Transaction> findByCustomerId(Long customerId);

    List<Transaction> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}