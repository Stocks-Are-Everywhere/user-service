package com.onseju.userservice.member.service;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;

import com.onseju.userservice.account.domain.Account;
import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.service.AccountRepository;
import com.onseju.userservice.account.service.dto.CreateOrderParams;
import com.onseju.userservice.holding.domain.Holdings;
import com.onseju.userservice.holding.service.HoldingsRepository;
import com.onseju.userservice.member.controller.request.ValidateRequest;
import com.onseju.userservice.member.controller.response.ValidateResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ValidateService {

	private final HoldingsRepository holdingsRepository;
	private final AccountRepository accountRepository;

	public ValidateResponse validate(ValidateRequest request) {
		Account account = accountRepository.getByMemberId(request.memberId());

		CreateOrderParams params = new CreateOrderParams(request.companyCode(),request.type(),request.totalQuantity(),request.price(),request.now(),request.memberId());
		Boolean valid = false;
		if(request.type() == Type.LIMIT_BUY || request.type() == Type.MARKET_BUY) {
			valid = validateAccount(params,account);
		} else {
			valid = validateHoldings(account.getId(),params);
		}

		return new ValidateResponse(valid, "응답",account.getId(),request.type());
	}


	private boolean validateAccount(final CreateOrderParams params, final Account account) {
			return account.validateDepositBalance(params.price().multiply(params.totalQuantity()));
	}

	private boolean validateHoldings(final Long accountId, final CreateOrderParams params) {
			final Holdings holdings = holdingsRepository.getByAccountIdAndCompanyCode(accountId, params.companyCode());
			boolean valid_one = holdings.validateExistHoldings();
			boolean valid_two = holdings.validateEnoughHoldings(params.totalQuantity());
			return valid_one && valid_two;
	}
}
