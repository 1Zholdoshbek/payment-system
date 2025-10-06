package com.example.paymentserviceapp.persistency;

import com.example.paymentserviceapp.persistence.entity.Payment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class PaymentFilterFactory {

    public static Specification<Payment> fromFilter(PaymentFilter filter) {
        Specification<Payment> spec = (root, query, cb) -> cb.conjunction();

        if (StringUtils.hasText(filter.currency())) {
            spec = spec.and(PaymentSpecifications.hasCurrency(filter.currency()));
        }

        if (filter.minAmount() != null || filter.maxAmount() != null) {
            spec = spec.and(PaymentSpecifications.amountBetween(filter.minAmount(), filter.maxAmount()));
        }

        if (filter.createdAfter() != null || filter.createdBefore() != null) {
            OffsetDateTime after = filter.createdAfter() != null ? OffsetDateTime.ofInstant(filter.createdAfter(), ZoneOffset.UTC) : OffsetDateTime.MIN;
            OffsetDateTime before = filter.createdBefore() != null ? OffsetDateTime.ofInstant(filter.createdBefore(), ZoneOffset.UTC) : OffsetDateTime.MAX;
            spec = spec.and(PaymentSpecifications.createdBetween(after, before));
        }

        if (filter.status() != null) {
            spec = spec.and(PaymentSpecifications.hasStatus(filter.status()));
        }

        return spec;
    }
}
