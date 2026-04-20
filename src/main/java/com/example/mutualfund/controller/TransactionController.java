package com.example.mutualfund.controller;

import com.example.mutualfund.dto.BuyRequest;
import com.example.mutualfund.dto.SellRequest;
import com.example.mutualfund.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestBody BuyRequest req) {
        return ResponseEntity.ok(service.buy(req));
    }

    @PostMapping("/sell")
    public ResponseEntity<?> sell(@RequestBody SellRequest req) {
        return ResponseEntity.ok(service.sell(req));
    }

    @GetMapping("/portfolio/{userId}")
    public ResponseEntity<?> portfolio(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getPortfolio(userId));
    }
}
