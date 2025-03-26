package com.onseju.userservice.member.controller.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.onseju.userservice.account.domain.Type;

public record ValidateRequest(String companyCode,
							  Type type,
							  BigDecimal totalQuantity,
							  BigDecimal price,
							  LocalDateTime now,
							  Long memberId
							  ) {
}
