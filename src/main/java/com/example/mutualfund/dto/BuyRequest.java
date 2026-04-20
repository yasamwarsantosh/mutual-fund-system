package com.example.mutualfund.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BuyRequest {
    private Long userId;
    private Long fundId;
    private BigDecimal amount;
    private String idempotencyKey;
}
