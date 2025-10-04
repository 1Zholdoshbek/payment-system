package com.example.paymentserviceapp.persistency;

import com.example.paymentserviceapp.persistence.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class PaymentFilter {
    private String currency;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Instant createdAfter;
    private Instant createdBefore;
    private PaymentStatus status;
}
