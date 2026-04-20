package com.example.mutualfund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mutualfund.entity.Transaction;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByIdempotencyKey(String key);
}
