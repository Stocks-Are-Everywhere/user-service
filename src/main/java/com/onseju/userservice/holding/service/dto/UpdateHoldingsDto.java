package com.onseju.userservice.holding.service.dto;

import com.onseju.userservice.account.domain.Type;

import java.math.BigDecimal;

public record UpdateHoldingsDto(
        Type type,
        Long accountId,
        String companyCode,
        BigDecimal price,
        BigDecimal quantity
) {
}
