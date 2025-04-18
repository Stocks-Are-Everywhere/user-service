package com.onseju.userservice.holding.service.dto;

import java.math.BigDecimal;

import com.onseju.userservice.account.domain.Type;

import lombok.Builder;

@Builder
public record AfterTradeHoldingsDto(
		Type type,
		Long accountId,
		String companyCode,
		BigDecimal price,
		BigDecimal quantity
) {
}
