package com.example.mutualfund.repository;

import com.example.mutualfund.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {}
