package com.example.paymentserviceapp.mapper;

import com.example.paymentserviceapp.dto.PaymentDto;
import com.example.paymentserviceapp.persistence.entity.Payment;
import com.example.paymentserviceapp.persistence.entity.PaymentStatus;

import static com.example.paymentserviceapp.mapper.TestConstants.*;

public class TestObjects {

    public static Payment createPaymentEntity() {
        Payment payment = new Payment();
        payment.setGuid(PAYMENT_GUID);
        payment.setInquiryRefId(INQUIRY_REF_ID);
        payment.setAmount(AMOUNT_1200);
        payment.setCurrency(CURRENCY_USD);
        payment.setTransactionRefId(TRANSACTION_REF_ID);
        payment.setStatus(PaymentStatus.CREATED);
        payment.setNote(NOTE_TEST_PAYMENT);
        payment.setCreatedAt(CREATED_AT);
        payment.setUpdatedAt(UPDATED_AT);
        return payment;
    }

    public static PaymentDto createPaymentDto() {
        return new PaymentDto(
                PAYMENT_GUID,
                INQUIRY_REF_ID,
                AMOUNT_550,
                CURRENCY_EUR,
                TRANSACTION_REF_ID,
                PaymentStatus.PENDING,
                NOTE_INVOICE_22,
                CREATED_AT.minusDays(1),
                UPDATED_AT
        );
    }
}
