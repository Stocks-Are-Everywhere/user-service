package com.onseju.userservice.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onseju.userservice.account.domain.Account;
import com.onseju.userservice.account.service.dto.AfterTradeAccountDto;
import com.onseju.userservice.account.service.dto.BeforeTradeAccountDto;
import com.onseju.userservice.account.service.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

	private final AccountRepository accountRepository;

	@Transactional
	public void updateAccountAfterTrade(final AfterTradeAccountDto dto) {
		Account account = accountRepository.getById(dto.accountId());
		account.processOrder(
				dto.type(),
				dto.price(),
				dto.quantity()
		);
	}

	@Transactional
	public void reserve(final BeforeTradeAccountDto dto) {
		if (dto.type().isBuy()) {
			Account account = accountRepository.getById(dto.accountId());
			account.processReservedOrder(dto.price().multiply(dto.totalQuantity()));
			accountRepository.save(account);
		}
	}
}
