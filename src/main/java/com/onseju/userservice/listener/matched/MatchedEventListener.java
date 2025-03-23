package com.onseju.userservice.listener.matched;

import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.service.AccountService;
import com.onseju.userservice.holding.service.HoldingsService;
import com.onseju.userservice.listener.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MatchedEventListener {

    private final AccountService accountService;
    private final HoldingsService holdingsService;
    private final EventMapper eventMapper;

    @Async
    @TransactionalEventListener
    public void handleMatchedEvent(final MatchedEvent matchedEvent) {

        // 1. 계좌 잔액 업데이트
        accountService.updateAccountAfterTrade(
                eventMapper.toAccountAfterTradeParams(matchedEvent, matchedEvent.buyAccountId(), Type.BUY));
        accountService.updateAccountAfterTrade(
                eventMapper.toAccountAfterTradeParams(matchedEvent, matchedEvent.sellAccountId(), Type.SELL));

        // 2. 보유 내역 업데이트
        holdingsService.updateHoldingsAfterTrade(
                eventMapper.toUpdateHoldingsParams(matchedEvent, matchedEvent.buyAccountId(), Type.BUY));
        holdingsService.updateHoldingsAfterTrade(
                eventMapper.toUpdateHoldingsParams(matchedEvent, matchedEvent.sellAccountId(), Type.SELL));
    }
}