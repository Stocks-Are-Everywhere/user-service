package com.onseju.userservice.member.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.onseju.userservice.account.domain.Type;

public record CreateOrderParams(
        String companyCode,
        Type type,
        BigDecimal totalQuantity,
        BigDecimal price,
        LocalDateTime now,
        Long memberId
) {
}
