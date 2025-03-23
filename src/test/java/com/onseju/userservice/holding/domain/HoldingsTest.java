package com.onseju.userservice.holding.domain;

import com.onseju.userservice.account.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class HoldingsTest {

    private Holdings holdings;

    @BeforeEach
    void setUp() {
        holdings = Holdings.builder()
                .quantity(BigDecimal.valueOf(100))
                .reservedQuantity(BigDecimal.valueOf(0))
                .averagePrice(BigDecimal.valueOf(1000))
                .totalPurchasePrice(BigDecimal.valueOf(100000))
                .build();
    }

    @Test
    @DisplayName("예약 주문 처리 시 예약된 주식 수량이 업데이트 되어야 한다")
    void processReservedOrder_shouldUpdateReservedQuantity() {
        // Given
        BigDecimal reservedQuantity = BigDecimal.valueOf(10);

        // When
        holdings.reserveOrder(reservedQuantity);

        // Then
        assertThat(holdings.getReservedQuantity()).isEqualTo(BigDecimal.valueOf(10));
    }

    @Test
    @DisplayName("매수 시 보유 주식 수량과 총 매수 금액이 업데이트 되어야 한다")
    void updateHoldings_whenBuy_shouldUpdateQuantityAndTotalPurchasePrice() {
        // Given
        BigDecimal updatePrice = BigDecimal.valueOf(100);
        BigDecimal updateQuantity = BigDecimal.valueOf(10);

        // When
        holdings.updateHoldings(Type.BUY, updatePrice, updateQuantity);

        // Then
        assertThat(holdings.getQuantity()).isEqualTo(BigDecimal.valueOf(110));
        assertThat(holdings.getTotalPurchasePrice()).isEqualTo(BigDecimal.valueOf(101_000));
        assertThat(holdings.getAveragePrice()).isEqualTo(new BigDecimal("918.1818"));
    }

    @Test
    @DisplayName("매도 시 보유 주식 수량과 총 매수 금액이 업데이트 되어야 한다")
    void updateHoldings_whenSell_shouldUpdateQuantityAndTotalPurchasePrice() {
        // Given
        BigDecimal updateQuantity = BigDecimal.valueOf(1);
        BigDecimal originalTotalQuantity = holdings.getTotalPurchasePrice();

        // When
        holdings.updateHoldings(Type.SELL, BigDecimal.ZERO, updateQuantity);

        // Then
        assertThat(holdings.getQuantity()).isEqualTo(BigDecimal.valueOf(99));
        assertThat(holdings.getTotalPurchasePrice().intValueExact()).isEqualTo(originalTotalQuantity.subtract(updateQuantity.multiply(holdings.getAveragePrice())).intValueExact());
    }

    @Test
    @DisplayName("보유한 주식을 모두 판매한 경우 삭제한다.")
    void deleteHoldings() {
        // given
        BigDecimal quantity = BigDecimal.valueOf(100);

        // when
        holdings.updateHoldings(Type.SELL, new BigDecimal(1000), quantity);

        // then
        assertThat(holdings.getDeletedDateTime()).isNotNull();
    }

    @Test
    @DisplayName("보유한 주식의 총 금액을 계산한다.")
    void calculateTotalPurchasePrice() {
        // given
        BigDecimal updatedQuantity = BigDecimal.valueOf(50);
        BigDecimal totalPurchasePrice = holdings.getTotalPurchasePrice();
        BigDecimal averagePrice = holdings.getAveragePrice();

        // when
        holdings.updateHoldings(Type.SELL, new BigDecimal(1000), updatedQuantity);

        // then
        assertThat(holdings.getTotalPurchasePrice()).isEqualTo(totalPurchasePrice.subtract(averagePrice.multiply(updatedQuantity)));
    }
}