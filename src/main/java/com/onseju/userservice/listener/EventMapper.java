package com.onseju.userservice.listener;

import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.service.dto.AccountAfterTradeParams;
import com.onseju.userservice.holding.service.dto.UpdateHoldingsDto;
import com.onseju.userservice.listener.matched.MatchedEvent;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public AccountAfterTradeParams toAccountAfterTradeParams(
            final MatchedEvent matchedEvent,
            final Long accountId,
            final Type type
    ) {
        return new AccountAfterTradeParams(
                accountId,
                type,
                matchedEvent.price(),
                matchedEvent.quantity()
        );
    }

    public UpdateHoldingsDto toUpdateHoldingsParams(
            final MatchedEvent matchedEvent,
            final Long accountId,
            final Type type
    ) {
        return new UpdateHoldingsDto(
                type,
                accountId,
                matchedEvent.companyCode(),
                matchedEvent.price(),
                matchedEvent.quantity()
        );
    }
}
