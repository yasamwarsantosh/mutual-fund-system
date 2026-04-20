package com.example.mutualfund.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId","fundId"}))
public class Portfolio {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long fundId;

    private BigDecimal totalUnits;
    private BigDecimal investedAmount;
}
