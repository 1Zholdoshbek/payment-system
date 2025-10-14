package com.example.paymentserviceapp.mapper;


import com.example.paymentserviceapp.dto.PaymentDto;
import com.example.paymentserviceapp.persistence.entity.Payment;
import com.example.paymentserviceapp.persistence.entity.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperTest {

    private PaymentMapper paymentMapper;

    @BeforeEach
    void setUp() {
        paymentMapper = Mappers.getMapper(PaymentMapper.class);
    }

    @Test
    void shouldMapPaymentToPaymentDto() {

        Payment payment = new Payment();
        payment.setGuid(UUID.randomUUID());
        payment.setInquiryRefId(UUID.randomUUID());
        payment.setAmount(new BigDecimal("1200.50"));
        payment.setCurrency("USD");
        payment.setTransactionRefId(UUID.randomUUID());
        payment.setStatus(PaymentStatus.CREATED);
        payment.setNote("Test payment");
        payment.setCreatedAt(OffsetDateTime.now().minusDays(1));
        payment.setUpdatedAt(OffsetDateTime.now());

        PaymentDto dto = paymentMapper.toPaymentDto(payment);

        assertNotNull(dto);
        assertEquals(payment.getGuid(), dto.guid());
        assertEquals(payment.getAmount(), dto.amount());
        assertEquals(payment.getCurrency(), dto.currency());
        assertEquals(payment.getStatus(), dto.status());
    }

    @Test
    void shouldMapPaymentDtoToPaymentEntity() {

        PaymentDto dto = new PaymentDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new BigDecimal("550.75"),
                "EUR",
                UUID.randomUUID(),
                PaymentStatus.PENDING,
                "Invoice #22",
                OffsetDateTime.now().minusDays(2),
                OffsetDateTime.now()
        );

        Payment payment = paymentMapper.toPaymentEntity(dto);

        assertNotNull(payment);
        assertEquals(dto.guid(), payment.getGuid());
        assertEquals(dto.amount(), payment.getAmount());
        assertEquals(dto.currency(), payment.getCurrency());
        assertEquals(dto.status(), payment.getStatus());
    }
}
