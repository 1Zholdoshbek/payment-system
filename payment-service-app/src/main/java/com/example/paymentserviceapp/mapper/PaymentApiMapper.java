package com.example.paymentserviceapp.mapper;

import com.example.paymentserviceapp.dto.PaymentDto;
import com.example.paymentserviceapp.dto.response.PaymentResponse;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface PaymentApiMapper {

    PaymentResponse toResponse(PaymentDto paymentDto);

    List<PaymentResponse> toResponseList(List<PaymentDto> paymentDtos);
}
