package com.onseju.userservice.order;

import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.mapper.AccountMapper;
import com.onseju.userservice.account.service.AccountService;
import com.onseju.userservice.holding.mapper.HoldingsMapper;
import com.onseju.userservice.holding.service.HoldingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderReservationService {

	private final AccountService accountService;
	private final AccountMapper accountMapper;

	private final HoldingsService holdingsService;
	private final HoldingsMapper holdingsMapper;

	public OrderReservationResponse validateUserInfoForOrder(final BeforeTradeOrderDto dto) {
		final Type type = convertType(dto.type());

		final Long accountId = accountService.reserve(accountMapper.toBeforeTradeAccountDto(dto, type));
		holdingsService.reserve(holdingsMapper.toBeforeTradeHoldingsDto(dto, type, accountId));

		return new OrderReservationResponse(accountId);
	}

	private Type convertType(final String type) {
		if (type.equals("LIMIT_SELL") || type.equals("MARKET_SELL")) {
			return Type.SELL;
		}
		return Type.BUY;
	}
}
