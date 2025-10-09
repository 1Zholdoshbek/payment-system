package com.example.paymentserviceapp.controller;

import com.example.paymentserviceapp.dto.PaymentDto;
import com.example.paymentserviceapp.dto.response.PaymentResponse;
import com.example.paymentserviceapp.mapper.PaymentApiMapper;
import com.example.paymentserviceapp.persistency.PaymentFilter;
import com.example.paymentserviceapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentApiMapper paymentApiMapper;

    private static final String DEFAULT_SORT_FIELD = "createdAt";
    private static final String SORT_DIRECTION_DESC = "desc";
    private static final String DEFAULT_SORT_DIRECTION = SORT_DIRECTION_DESC;
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_PAGE_SIZE = "20";

    @GetMapping
    public List<PaymentResponse> getPayments() {
        List<PaymentDto> dtos = paymentService.getAllPayments();
        return paymentApiMapper.toResponseList(dtos);
    }

    @GetMapping("/{guid}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable UUID guid) {
        PaymentDto paymentDto = paymentService.getPaymentById(guid);
        return ResponseEntity.ok(paymentApiMapper.toResponse(paymentDto));
    }

    @GetMapping("/search")
    public Page<PaymentResponse> searchPayments(
            @ModelAttribute PaymentFilter filter,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortBy,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String direction
    ) {
        Sort sort = direction.equalsIgnoreCase(SORT_DIRECTION_DESC)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return paymentService.searchPaged(filter, pageable)
                .map(paymentApiMapper::toResponse);
    }
}