package com.onseju.userservice.listener.ordered;

import com.onseju.userservice.account.domain.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderedEvent(
        Long id,
        String companyCode,
        Type type,
        BigDecimal totalQuantity,
        BigDecimal remainingQuantity,
        BigDecimal price,
        LocalDateTime createdDateTime,
        Long accountId
) {
}
