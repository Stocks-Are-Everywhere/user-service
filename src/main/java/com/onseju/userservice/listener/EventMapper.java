package com.onseju.userservice.listener;

import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.service.dto.AccountAfterTradeParams;
import com.onseju.userservice.account.service.dto.ReserveAccountDto;
import com.onseju.userservice.holding.service.dto.UpdateHoldingsDto;
import com.onseju.userservice.listener.matched.MatchedEvent;
import com.onseju.userservice.listener.ordered.OrderedEvent;
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

    public ReserveAccountDto toReserveAccountDto(final OrderedEvent orderedEvent) {
        return new ReserveAccountDto(
                orderedEvent.accountId(),
                orderedEvent.type(),
                orderedEvent.price(),
                orderedEvent.totalQuantity()
        );
    }

    public UpdateHoldingsDto toUpdateHoldingsDto(final OrderedEvent orderedEvent) {
        return new UpdateHoldingsDto(
                orderedEvent.type(),
                orderedEvent.accountId(),
                orderedEvent.companyCode(),
                orderedEvent.price(),
                orderedEvent.totalQuantity()
        );
    }
}
