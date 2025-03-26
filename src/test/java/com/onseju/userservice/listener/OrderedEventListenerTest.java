package com.onseju.userservice.listener;

import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.service.AccountService;
import com.onseju.userservice.holding.service.HoldingsService;
import com.onseju.userservice.listener.ordered.OrderedEvent;
import com.onseju.userservice.listener.ordered.OrderedEventListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(MockitoExtension.class)
class OrderedEventListenerTest {

	@Mock
	private AccountService accountService;

	@Mock
	private HoldingsService holdingsService;

	@Mock
	private EventMapper eventMapper;

	@InjectMocks
	private OrderedEventListener orderedEventListener;

	@Test
	@DisplayName("지정가 매도 이벤트를 전달받아 처리한다.")
	void handleLimitSellOrderedEvent() {
		// given
		OrderedEvent orderedEvent = createOrderedEvent(Type.LIMIT_SELL);

		// when & then
		assertThatCode(() -> orderedEventListener.handleOrderedEvent(orderedEvent))
			.doesNotThrowAnyException();

		verify(accountService).reserve(any());
		verify(holdingsService).updateHoldingsAfterTrade(any());
	}

	@Test
	@DisplayName("시장가 매도 이벤트를 전달받아 처리한다.")
	void handleMarketSellOrderedEvent() {
		// given
		OrderedEvent orderedEvent = createOrderedEvent(Type.MARKET_SELL);

		// when & then
		assertThatCode(() -> orderedEventListener.handleOrderedEvent(orderedEvent))
			.doesNotThrowAnyException();

		verify(accountService).reserve(any());
		verify(holdingsService).updateHoldingsAfterTrade(any());
	}

	@Test
	@DisplayName("지정가 매수 이벤트를 전달받아 처리한다.")
	void handleLimitBuyOrderedEvent() {
		// given
		OrderedEvent orderedEvent = createOrderedEvent(Type.LIMIT_BUY);

		// when & then
		assertThatCode(() -> orderedEventListener.handleOrderedEvent(orderedEvent))
			.doesNotThrowAnyException();

		verify(accountService).reserve(any());
		verify(holdingsService).updateHoldingsAfterTrade(any());
	}

	@Test
	@DisplayName("시장가 매수 이벤트를 전달받아 처리한다.")
	void handleMarketBuyOrderedEvent() {
		// given
		OrderedEvent orderedEvent = createOrderedEvent(Type.MARKET_BUY);

		// when & then
		assertThatCode(() -> orderedEventListener.handleOrderedEvent(orderedEvent))
			.doesNotThrowAnyException();

		verify(accountService).reserve(any());
		verify(holdingsService).updateHoldingsAfterTrade(any());
	}

	private OrderedEvent createOrderedEvent(Type type) {
		return new OrderedEvent(
			1L,
			"005930",
			type,
			new BigDecimal("10"),
			BigDecimal.ZERO,
			BigDecimal.valueOf(1000),
			LocalDateTime.of(2025, 1, 1, 1, 1),
			1L
		);
	}
}
