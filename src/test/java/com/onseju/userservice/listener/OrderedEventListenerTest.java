// package com.onseju.userservice.listener;
//
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.*;
//
// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.TimeUnit;
//
// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
//
// import com.onseju.userservice.account.domain.Type;
// import com.onseju.userservice.account.service.AccountService;
// import com.onseju.userservice.events.listener.OrderedEventListener;
// import com.onseju.userservice.events.mapper.EventMapper;
// import com.onseju.userservice.global.BeforeTradeOrderDto;
// import com.onseju.userservice.holding.service.HoldingsService;
//
// @ExtendWith(MockitoExtension.class)
// public class OrderedEventListenerTest {
//
// 	@InjectMocks
// 	private OrderedEventListener orderedEventListener;
//
// 	@Mock
// 	private AccountService accountService;
//
// 	@Mock
// 	private HoldingsService holdingsService;
//
// 	private final EventMapper eventMapper = new EventMapper();
//
// 	@BeforeEach
// 	void setUp() {
// 		orderedEventListener = new OrderedEventListener(holdingsService, accountService, eventMapper);
// 	}
//
// 	@Test
// 	@DisplayName("이벤트를 전달받아 비동기로 처리한다.")
// 	void handleOrderedEvent() {
// 		// given
// 		BeforeTradeOrderDto orderedEvent = new BeforeTradeOrderDto(
// 				1L,
// 				"005930",
// 				Type.SELL,
// 				new BigDecimal(10),
// 				BigDecimal.ZERO,
// 				BigDecimal.valueOf(1000),
// 				LocalDateTime.of(2025, 1, 1, 1, 1),
// 				1L
// 		);
//
// 		// when
// 		CompletableFuture.runAsync(() -> orderedEventListener.handleOrderedEvent(orderedEvent))
// 				.orTimeout(2, TimeUnit.SECONDS) // 비동기 실행을 기다림
// 				.join();
//
// 		// then
// 		Assertions.assertThatCode(() -> orderedEventListener.handleOrderedEvent(orderedEvent))
// 				.doesNotThrowAnyException();
// 	}
//
// 	@Test
// 	@DisplayName("이벤트 내용을 처리하기 위해 Account, Holdings에 예약 처리한다.")
// 	void processOrderedEvent() {
// 		// given
// 		BeforeTradeOrderDto orderedEvent = new BeforeTradeOrderDto(
// 				1L,
// 				"005930",
// 				Type.SELL,
// 				new BigDecimal(10),
// 				BigDecimal.ZERO,
// 				BigDecimal.valueOf(1000),
// 				LocalDateTime.of(2025, 1, 1, 1, 1),
// 				1L
// 		);
//
// 		// when
// 		orderedEventListener.handleOrderedEvent(orderedEvent);
//
// 		// then
// 		verify(accountService).reserve(any());
// 		verify(holdingsService).updateHoldingsAfterTrade(any());
// 	}
// }
