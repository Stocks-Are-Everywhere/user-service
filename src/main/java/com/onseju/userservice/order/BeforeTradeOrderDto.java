package com.onseju.userservice.order;

import java.math.BigDecimal;

public record BeforeTradeOrderDto(
		String companyCode,
		String type,
		BigDecimal totalQuantity,
		BigDecimal price,
		Long timestamp,
		Long memberId
) {
}
