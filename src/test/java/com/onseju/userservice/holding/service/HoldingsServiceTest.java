package com.onseju.userservice.holding.service;

import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.holding.fake.FakeHoldingsRepository;
import com.onseju.userservice.holding.domain.Holdings;
import com.onseju.userservice.holding.service.dto.UpdateHoldingsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class HoldingsServiceTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final String COMPANY_CODE = "005930";
    private final HoldingsRepository holdingsRepository = new FakeHoldingsRepository();
    private final HoldingsService holdingsService = new HoldingsService(holdingsRepository);

    @Test
    @DisplayName("보유 내역이 존재할 경우 이전 보유 내역과 합친다.")
    void getExistingHoldings() {
        // given
        Holdings holdings = createHoldings(new BigDecimal("100"));
        holdingsRepository.save(holdings);
        UpdateHoldingsDto params = new UpdateHoldingsDto(Type.LIMIT_SELL, ACCOUNT_ID, COMPANY_CODE, new BigDecimal(1000), new BigDecimal(10));

        // when
        holdingsService.updateHoldingsAfterTrade(params);

        // then
        Holdings updatedHoldings = holdingsRepository.getByAccountIdAndCompanyCode(ACCOUNT_ID, COMPANY_CODE);
        assertThat(updatedHoldings.getReservedQuantity()).isEqualTo(BigDecimal.ZERO);
        assertThat(updatedHoldings.getQuantity()).isEqualTo(new BigDecimal(90));
    }

    @Test
    @DisplayName("시장가 매도 시 보유 내역이 존재할 경우 이전 보유 내역과 합친다.")
    void getExistingHoldingsWithMarketSell() {
        // given
        Holdings holdings = createHoldings(new BigDecimal("100"));
        holdingsRepository.save(holdings);
        UpdateHoldingsDto params = new UpdateHoldingsDto(Type.MARKET_SELL, ACCOUNT_ID, COMPANY_CODE, new BigDecimal(1000), new BigDecimal(10));

        // when
        holdingsService.updateHoldingsAfterTrade(params);

        // then
        Holdings updatedHoldings = holdingsRepository.getByAccountIdAndCompanyCode(ACCOUNT_ID, COMPANY_CODE);
        assertThat(updatedHoldings.getReservedQuantity()).isEqualTo(BigDecimal.ZERO);
        assertThat(updatedHoldings.getQuantity()).isEqualTo(new BigDecimal(90));
    }

    @Test
    @DisplayName("지정가 매수 시 보유 내역이 존재하지 않을 경우 새롭게 생성하여 저장한다.")
    void createHoldingsWithLimitBuy() {
        // given
        UpdateHoldingsDto params = new UpdateHoldingsDto(Type.LIMIT_BUY, ACCOUNT_ID, COMPANY_CODE, new BigDecimal(1000), new BigDecimal(10));

        // when
        holdingsService.updateHoldingsAfterTrade(params);

        // then
        Holdings updatedHoldings = holdingsRepository.getByAccountIdAndCompanyCode(ACCOUNT_ID, COMPANY_CODE);
        assertThat(updatedHoldings.getCompanyCode()).isEqualTo(COMPANY_CODE);
        assertThat(updatedHoldings.getReservedQuantity()).isEqualTo(BigDecimal.ZERO);
        assertThat(updatedHoldings.getQuantity()).isEqualTo(new BigDecimal(10));
    }

    @Test
    @DisplayName("시장가 매수 시 보유 내역이 존재하지 않을 경우 새롭게 생성하여 저장한다.")
    void createHoldingsWithMarketBuy() {
        // given
        UpdateHoldingsDto params = new UpdateHoldingsDto(Type.MARKET_BUY, ACCOUNT_ID, COMPANY_CODE, new BigDecimal(1000), new BigDecimal(10));

        // when
        holdingsService.updateHoldingsAfterTrade(params);

        // then
        Holdings updatedHoldings = holdingsRepository.getByAccountIdAndCompanyCode(ACCOUNT_ID, COMPANY_CODE);
        assertThat(updatedHoldings.getCompanyCode()).isEqualTo(COMPANY_CODE);
        assertThat(updatedHoldings.getReservedQuantity()).isEqualTo(BigDecimal.ZERO);
        assertThat(updatedHoldings.getQuantity()).isEqualTo(new BigDecimal(10));
    }

    private Holdings createHoldings(BigDecimal quantity) {
        return Holdings.builder()
            .companyCode("005930")
            .quantity(quantity)
            .reservedQuantity(new BigDecimal(10))
            .averagePrice(new BigDecimal(1000))
            .totalPurchasePrice(new BigDecimal(10000))
            .accountId(ACCOUNT_ID)
            .build();
    }
}

