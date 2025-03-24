package com.onseju.userservice.order.controller.request;

import com.onseju.userservice.account.domain.Type;

import java.math.BigDecimal;

public record OrderValidationRequest(
        Long memberId,
        String companyCode,
        Type type,
        BigDecimal price,
        BigDecimal quantity
) {
}
