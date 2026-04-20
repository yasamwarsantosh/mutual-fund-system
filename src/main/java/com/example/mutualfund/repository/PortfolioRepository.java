package com.example.mutualfund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.mutualfund.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUserIdAndFundId(Long userId, Long fundId);
}
