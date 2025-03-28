package com.onseju.userservice.events;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record MatchedEvent(
		UUID id,
		String companyCode,
		Long buyOrderId,
		Long buyAccountId,
		Long sellOrderId,
		Long sellAccountId,
		BigDecimal quantity,
		BigDecimal price,
		Long tradeAt
) {
}
