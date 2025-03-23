package com.onseju.userservice.listener;

import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.service.AccountService;
import com.onseju.userservice.holding.service.HoldingsService;
import com.onseju.userservice.listener.ordered.OrderedEvent;
import com.onseju.userservice.listener.ordered.OrderedEventListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderedEventListenerTest {

    @InjectMocks
    private OrderedEventListener orderedEventListener;

    @Mock
    private AccountService accountService;

    @Mock
    private HoldingsService holdingsService;

    private final EventMapper eventMapper = new EventMapper();

    @BeforeEach
    void setUp() {
        orderedEventListener = new OrderedEventListener(holdingsService, accountService, eventMapper);
    }

    @Test
    @DisplayName("이벤트를 전달받아 비동기로 처리한다.")
    void handleOrderedEvent() {
        // given
        OrderedEvent orderedEvent = new OrderedEvent(
                1L,
                "005930",
                Type.SELL,
                new BigDecimal(10),
                BigDecimal.ZERO,
                BigDecimal.valueOf(1000),
                LocalDateTime.of(2025, 1, 1, 1, 1),
                1L
        );

        // when
        CompletableFuture.runAsync(() -> orderedEventListener.handleOrderedEvent(orderedEvent))
                .orTimeout(2, TimeUnit.SECONDS) // 비동기 실행을 기다림
                .join();

        // then
        Assertions.assertThatCode(() -> orderedEventListener.handleOrderedEvent(orderedEvent))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이벤트 내용을 처리하기 위해 Account, Holdings에 예약 처리한다.")
    void processOrderedEvent() {
        // given
        OrderedEvent orderedEvent = new OrderedEvent(
                1L,
                "005930",
                Type.SELL,
                new BigDecimal(10),
                BigDecimal.ZERO,
                BigDecimal.valueOf(1000),
                LocalDateTime.of(2025, 1, 1, 1, 1),
                1L
        );

        // when
        orderedEventListener.handleOrderedEvent(orderedEvent);

        // then
        verify(accountService).reserve(any());
        verify(holdingsService).updateHoldingsAfterTrade(any());
    }
}
