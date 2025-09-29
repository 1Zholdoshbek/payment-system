package com.example.paymentserviceapp.controller;
import com.example.paymentserviceapp.model.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Map<Long, Payment> payments = new HashMap<>();

    static {
        payments.put(1L, new Payment(1L, 100.0));
        payments.put(2L, new Payment(2L, 200.0));
        payments.put(3L, new Payment(3L, 300.0));

    }

    @GetMapping
    public List<Payment> getPayments() {
        return new ArrayList<>(payments.values());
    }

    @GetMapping("{id}")
    public Payment getPayment(@PathVariable Long id) {
        return payments.get(id);
    }
}

