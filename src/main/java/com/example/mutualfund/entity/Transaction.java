package com.example.mutualfund.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long fundId;

    private String type;

    private BigDecimal units;
    private BigDecimal amount;

    @Column(unique = true)
    private String idempotencyKey;

    private LocalDateTime createdAt = LocalDateTime.now();
}
