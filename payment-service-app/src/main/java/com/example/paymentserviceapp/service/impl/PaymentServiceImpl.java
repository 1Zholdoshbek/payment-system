package com.example.paymentserviceapp.service.impl;
import com.example.paymentserviceapp.dto.PaymentDto;
import com.example.paymentserviceapp.mapper.PaymentMapper;
import com.example.paymentserviceapp.persistence.entity.Payment;
import com.example.paymentserviceapp.persistency.PaymentFilter;
import com.example.paymentserviceapp.persistency.PaymentFilterFactory;
import com.example.paymentserviceapp.persistency.PaymentRepository;
import com.example.paymentserviceapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().
                stream()
                .map(paymentMapper::toPaymentDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto getPaymentById(UUID guid) {
        Payment payment = paymentRepository.getById(guid);
        return paymentMapper.toPaymentDto(payment);
    }

    @Override
    public Page<PaymentDto> searchPaged(PaymentFilter paymentFilter, Pageable pageable) {
        Specification<Payment> spec = PaymentFilterFactory.fromFilter(paymentFilter);
        return paymentRepository.findAll(spec, pageable).
                map(paymentMapper::toPaymentDto);
    }
}
