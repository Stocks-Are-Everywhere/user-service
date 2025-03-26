package com.onseju.userservice.holdings.fake;

import com.onseju.userservice.holdings.domain.Holdings;
import com.onseju.userservice.holdings.service.HoldingsRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

public class FakeHoldingsRepository implements HoldingsRepository {

    private final ConcurrentSkipListSet<Holdings> elements = new ConcurrentSkipListSet<>(Comparator.comparing(Holdings::getAccountId));

    @Override
    public Holdings getByAccountIdAndCompanyCode(Long accountId, String companyCode) {
        return elements.stream()
                .filter(value ->
                        value.getCompanyCode().equals(companyCode) &&
                                value.getAccountId().equals(accountId)
                )
                .findAny()
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

    public Holdings save(Holdings holdings) {
        if (hasElement(holdings)) {
            elements.stream()
                    .filter(o -> o.getId().equals(holdings.getId()))
                    .forEach(elements::remove);
            elements.add(holdings);
            return holdings;
        }
        Holdings saved = Holdings.builder()
                .id((long) elements.size() + 1)
                .companyCode(holdings.getCompanyCode())
                .quantity(holdings.getQuantity())
                .reservedQuantity(holdings.getReservedQuantity())
                .averagePrice(holdings.getAveragePrice())
                .totalPurchasePrice(holdings.getTotalPurchasePrice())
                .accountId(holdings.getAccountId())
                .build();
        elements.add(saved);
        return saved;
    }

    private boolean hasElement(Holdings holdings) {
        if (holdings.getId() == null) {
            return false;
        }
        return elements.stream()
                .anyMatch(o -> o.getId().equals(holdings.getId()));
    }
}
