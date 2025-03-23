package com.onseju.userservice.account.service.dto;

import com.onseju.userservice.account.domain.Type;

import java.math.BigDecimal;

public record ReserveAccountDto(
        Long accountId,
        Type type,
        BigDecimal price,
        BigDecimal totalQuantity
) {
}
