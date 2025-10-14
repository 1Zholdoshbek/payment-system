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
        return paymentRepository.findAll().stream()
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

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment =  paymentMapper.toPaymentEntity(paymentDto);
        payment.setGuid(null);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toPaymentDto(savedPayment);
    }

    @Override
    public PaymentDto updatePayment(UUID id, PaymentDto dto) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalArgumentException("Платеж не найден: " + id);
        }
        Payment updated = paymentMapper.toPaymentEntity(dto);
        updated.setGuid(id);
        Payment saved = paymentRepository.save(updated);
        return paymentMapper.toPaymentDto(saved);
    }

    @Override
    public void delete(UUID id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalArgumentException("Платеж не найден: " + id);
        }
        paymentRepository.deleteById(id);
    }
}
