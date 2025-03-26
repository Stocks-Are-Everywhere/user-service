package com.onseju.userservice.global;

import org.springframework.stereotype.Service;

import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.mapper.AccountMapper;
import com.onseju.userservice.account.service.AccountService;
import com.onseju.userservice.account.service.dto.BeforeTradeAccountDto;
import com.onseju.userservice.holding.mapper.HoldingsMapper;
import com.onseju.userservice.holding.service.HoldingsService;
import com.onseju.userservice.holding.service.dto.BeforeTradeHoldingsDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserValidateService {

	private final AccountService accountService;
	private final AccountMapper accountMapper;

	private final HoldingsService holdingsService;
	private final HoldingsMapper holdingsMapper;

	public void validateUserInfoForOrder(final BeforeTradeOrderDto dto) {
		final Type type = convertType(dto.type());

		final BeforeTradeAccountDto beforeTradeAccountDto = accountMapper.toBeforeTradeAccountDto(dto, type);
		accountService.reserve(beforeTradeAccountDto);

		final BeforeTradeHoldingsDto beforeTradeHoldingsDto = holdingsMapper.toBeforeTradeHoldingsDto(dto, type);
		holdingsService.reserve(beforeTradeHoldingsDto);
	}

	private Type convertType(final String type) {
		if (type.equals("LIMIT_SELL") || type.equals("MARKET_SELL")) {
			return Type.SELL;
		}
		return Type.BUY;
	}
}
