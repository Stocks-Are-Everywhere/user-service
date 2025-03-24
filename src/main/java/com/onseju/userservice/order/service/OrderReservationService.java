package com.onseju.userservice.order.service;

import com.onseju.userservice.account.service.AccountService;
import com.onseju.userservice.holding.service.HoldingsService;
import com.onseju.userservice.order.controller.request.OrderValidationRequest;
import com.onseju.userservice.order.controller.response.OrderValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderReservationService {

    private final AccountService accountService;
    private final HoldingsService holdingsService;

    public OrderValidationResponse reserve(OrderValidationRequest request) {
        Long accountId = accountService.checkAccountAndReserve(request);
        holdingsService.reserveHoldings(request, accountId);
        return new OrderValidationResponse(accountId);
    }
}
