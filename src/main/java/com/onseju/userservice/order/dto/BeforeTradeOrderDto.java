package com.onseju.userservice.order.dto;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BeforeTradeOrderDto(
		String companyCode,
		String type,
		BigDecimal totalQuantity,
		BigDecimal price,
		Long timestamp,
		Long memberId
) {

}
