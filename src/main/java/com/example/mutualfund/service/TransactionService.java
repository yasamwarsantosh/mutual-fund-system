package com.example.mutualfund.service;

import com.example.mutualfund.dto.BuyRequest;
import com.example.mutualfund.dto.SellRequest;
import com.example.mutualfund.entity.Fund;
import com.example.mutualfund.entity.Portfolio;
import com.example.mutualfund.entity.Transaction;
import com.example.mutualfund.entity.User;
import com.example.mutualfund.repository.FundRepository;
import com.example.mutualfund.repository.PortfolioRepository;
import com.example.mutualfund.repository.TransactionRepository;
import com.example.mutualfund.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepo;
    private final FundRepository fundRepo;
    private final PortfolioRepository portfolioRepo;
    private final TransactionRepository txnRepo;

    @Transactional
    public String buy(BuyRequest request) {

        if (txnRepo.findByIdempotencyKey(request.getIdempotencyKey()).isPresent()) {
            return "Duplicate request";
        }

        User user = userRepo.findById(request.getUserId()).orElseThrow();
        Fund fund = fundRepo.findById(request.getFundId()).orElseThrow();

        if (user.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal units = request.getAmount()
                .divide(fund.getNav(), 4, RoundingMode.HALF_UP);

        user.setBalance(user.getBalance().subtract(request.getAmount()));

        Portfolio portfolio = portfolioRepo
                .findByUserIdAndFundId(user.getId(), fund.getId())
                .orElse(new Portfolio());

        portfolio.setUserId(user.getId());
        portfolio.setFundId(fund.getId());
        portfolio.setTotalUnits(
                Optional.ofNullable(portfolio.getTotalUnits()).orElse(BigDecimal.ZERO).add(units));
        portfolio.setInvestedAmount(
                Optional.ofNullable(portfolio.getInvestedAmount()).orElse(BigDecimal.ZERO)
                        .add(request.getAmount()));

        Transaction txn = new Transaction();
        txn.setUserId(user.getId());
        txn.setFundId(fund.getId());
        txn.setType("BUY");
        txn.setUnits(units);
        txn.setAmount(request.getAmount());
        txn.setIdempotencyKey(request.getIdempotencyKey());

        txnRepo.save(txn);
        portfolioRepo.save(portfolio);
        userRepo.save(user);

        return "Buy successful";
    }

    @Transactional
    public String sell(SellRequest request) {

        if (txnRepo.findByIdempotencyKey(request.getIdempotencyKey()).isPresent()) {
            return "Duplicate request";
        }

        Portfolio portfolio = portfolioRepo
                .findByUserIdAndFundId(request.getUserId(), request.getFundId())
                .orElseThrow(() -> new RuntimeException("No holdings"));

        if (portfolio.getTotalUnits().compareTo(request.getUnits()) < 0) {
            throw new RuntimeException("Insufficient units");
        }

        Fund fund = fundRepo.findById(request.getFundId()).orElseThrow();

        BigDecimal amount = request.getUnits().multiply(fund.getNav());

        portfolio.setTotalUnits(portfolio.getTotalUnits().subtract(request.getUnits()));

        User user = userRepo.findById(request.getUserId()).orElseThrow();
        user.setBalance(user.getBalance().add(amount));

        Transaction txn = new Transaction();
        txn.setUserId(request.getUserId());
        txn.setFundId(request.getFundId());
        txn.setType("SELL");
        txn.setUnits(request.getUnits());
        txn.setAmount(amount);
        txn.setIdempotencyKey(request.getIdempotencyKey());

        txnRepo.save(txn);
        portfolioRepo.save(portfolio);
        userRepo.save(user);

        return "Sell successful";
    }

    public List<Portfolio> getPortfolio(Long userId) {
        return portfolioRepo.findAll().stream()
                .filter(p -> p.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
