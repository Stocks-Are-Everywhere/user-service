package com.onseju.userservice.holding.repository;

import com.onseju.userservice.holding.domain.Holdings;
import com.onseju.userservice.holding.service.HoldingsRepository;
import com.onseju.userservice.holding.exception.HoldingsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class HoldingsRepositoryImpl implements HoldingsRepository {

    private final HoldingsJpaRepository holdingsJpaRepository;

    @Override
    public Holdings save(final Holdings holdings) {
        return holdingsJpaRepository.save(holdings);
    }

    @Override
    public Holdings getOrDefaultByAccountIdAndCompanyCode(final Long accountId, final String companyCode) {
        return holdingsJpaRepository.findByAccountIdAndCompanyCode(accountId, companyCode)
                .orElse(
                        Holdings.builder()
                                .accountId(accountId)
                                .companyCode(companyCode)
                                .quantity(BigDecimal.ZERO)
                                .reservedQuantity(BigDecimal.ZERO)
                                .averagePrice(BigDecimal.ZERO)
                                .totalPurchasePrice(BigDecimal.ZERO)
                                .build()
                );
    }

    @Override
    public Holdings getByAccountIdAndCompanyCode(final Long accountId, final String companyCode) {
        return holdingsJpaRepository.findByAccountIdAndCompanyCode(accountId, companyCode)
                .orElseThrow(HoldingsNotFoundException::new);
    }
}
