package com.example.mutualfund.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SellRequest {
    private Long userId;
    private Long fundId;
    private BigDecimal units;
    private String idempotencyKey;
}
