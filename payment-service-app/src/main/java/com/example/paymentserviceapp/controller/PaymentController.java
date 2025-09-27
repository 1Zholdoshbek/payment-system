package com.example.paymentserviceapp.controller;
import com.example.paymentserviceapp.persistence.entity.Payment;
import com.example.paymentserviceapp.persistency.PaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @GetMapping
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("{guid}")
    public ResponseEntity<Payment> getPayment(@PathVariable UUID guid) {
        return paymentRepository.findById(guid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

