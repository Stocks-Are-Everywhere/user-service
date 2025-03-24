package com.onseju.userservice.account.service;

import com.onseju.userservice.account.domain.Account;
import com.onseju.userservice.account.service.dto.AccountAfterTradeParams;
import com.onseju.userservice.order.controller.request.OrderValidationRequest;
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
	public Long checkAccountAndReserve(final OrderValidationRequest request) {
		Account account = accountRepository.getByMemberId(request.memberId());
		if (request.type().isBuy()) {
			account.validateDepositBalance(request.price().multiply(request.quantity()));
			account.processReservedOrder(request.price().multiply(request.quantity()));
		}
		return account.getId();
	}
}
