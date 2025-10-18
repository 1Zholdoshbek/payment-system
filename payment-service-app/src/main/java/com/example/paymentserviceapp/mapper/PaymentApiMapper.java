package com.example.paymentserviceapp.mapper;

import com.example.paymentserviceapp.dto.PaymentDto;
import com.example.paymentserviceapp.dto.request.PaymentRequest;
import com.example.paymentserviceapp.dto.response.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PaymentApiMapper {

    @Mapping(target = "guid", source = "guid")
    @Mapping(target = "inquiryRefId", source = "inquiryRefId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "transactionRefId", source = "transactionRefId")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "note", source = "note")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PaymentResponse toResponse(PaymentDto paymentDto);

    List<PaymentResponse> toResponseList(List<PaymentDto> paymentDtos);

    @Mapping(target = "guid", ignore = true)
    @Mapping(target = "transactionRefId", ignore = true)
    @Mapping(target = "status", constant = "CREATED") // или PaymentStatus.CREATED
    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
    PaymentDto toDto(PaymentRequest request);
}

