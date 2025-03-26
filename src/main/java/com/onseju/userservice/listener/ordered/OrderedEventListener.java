package com.onseju.userservice.listener.ordered;

import com.onseju.userservice.account.service.AccountService;
import com.onseju.userservice.holdings.service.HoldingsService;
import com.onseju.userservice.listener.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderedEventListener {

    private final HoldingsService holdingsService;
    private final AccountService accountService;
    private final EventMapper eventMapper;

    /**
     * 주문 생성 이벤트를 받아 Account와 Holdings에 예약 처리를 한다.
     */
    @Async
    @EventListener
    public void handleOrderedEvent(OrderedEvent orderedEvent) {
        accountService.reserve(eventMapper.toReserveAccountDto(orderedEvent));
        holdingsService.updateHoldingsAfterTrade(eventMapper.toUpdateHoldingsDto(orderedEvent));
    }
}
