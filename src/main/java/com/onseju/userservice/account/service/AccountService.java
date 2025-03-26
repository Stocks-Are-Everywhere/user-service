package com.onseju.userservice.account.service;

import com.onseju.userservice.account.domain.Account;
import com.onseju.userservice.account.service.dto.AccountAfterTradeParams;
import com.onseju.userservice.account.service.dto.CreateOrderParams;
import com.onseju.userservice.account.service.dto.ReserveAccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

	private final AccountRepository accountRepository;

	@Transactional
	public void updateAccountAfterTrade(final AccountAfterTradeParams accountAfterTradeParams) {
		Account account = accountRepository.getById(accountAfterTradeParams.accountId());
		account.processOrder(
				accountAfterTradeParams.type(),
				accountAfterTradeParams.price(),
				accountAfterTradeParams.quantity()
		);
	}

	@Transactional
	public void reserve(final ReserveAccountDto dto) {
		if (dto.type().isBuy()) {
			Account account = accountRepository.getById(dto.accountId());
			account.processReservedOrder(dto.price().multiply(dto.totalQuantity()));
		}
	}

	public void validateDepositBalance(final CreateOrderParams params, final Account account) {

	}
}
