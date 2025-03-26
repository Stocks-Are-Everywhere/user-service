package com.onseju.userservice.account.mapper;

import org.springframework.stereotype.Component;

import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.service.dto.AfterTradeAccountDto;
import com.onseju.userservice.account.service.dto.BeforeTradeAccountDto;
import com.onseju.userservice.events.UpdateEvent;
import com.onseju.userservice.global.BeforeTradeOrderDto;

@Component
public class AccountMapper {

	public AfterTradeAccountDto toAfterTradeAccountDto(
			final UpdateEvent event,
			final Long accountId,
			final Type type
	) {
		return AfterTradeAccountDto.builder()
				.accountId(accountId)
				.type(type)
				.price(event.price())
				.quantity(event.quantity())
				.build();
	}

	public BeforeTradeAccountDto toBeforeTradeAccountDto(final BeforeTradeOrderDto dto, final Type type) {
		return BeforeTradeAccountDto.builder()
				.accountId(dto.accountId())
				.type(type)
				.price(dto.price())
				.totalQuantity(dto.totalQuantity())
				.build();
	}
}
