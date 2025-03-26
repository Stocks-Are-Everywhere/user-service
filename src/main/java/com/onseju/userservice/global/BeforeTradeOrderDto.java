package com.onseju.userservice.global;

import java.math.BigDecimal;

public record BeforeTradeOrderDto(
		String companyCode,
		String type,
		BigDecimal totalQuantity,
		BigDecimal price,
		Long timestamp,
		Long accountId
) {
}
