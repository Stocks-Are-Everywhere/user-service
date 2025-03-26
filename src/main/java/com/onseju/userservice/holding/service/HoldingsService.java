package com.onseju.userservice.holding.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onseju.userservice.holding.domain.Holdings;
import com.onseju.userservice.holding.service.dto.AfterTradeHoldingsDto;
import com.onseju.userservice.holding.service.dto.BeforeTradeHoldingsDto;
import com.onseju.userservice.holding.service.repository.HoldingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HoldingsService {

	private final HoldingsRepository holdingsRepository;

	@Transactional
	public void updateHoldingsAfterTrade(final AfterTradeHoldingsDto params) {
		final Holdings holdings = holdingsRepository.getByAccountIdAndCompanyCode(params.accountId(),
				params.companyCode());
		holdings.updateHoldings(params.type(), params.price(), params.quantity());
		holdingsRepository.save(holdings);
	}

	@Transactional
	public void reserve(final BeforeTradeHoldingsDto dto) {
		if (dto.type().isSell()) {
			final Holdings holdings = holdingsRepository.getByAccountIdAndCompanyCode(dto.accountId(),
					dto.companyCode());
			holdings.validateExistHoldings();
			holdings.validateEnoughHoldings(dto.totalQuantity());
			holdings.reserveOrder(dto.totalQuantity());
			holdingsRepository.save(holdings);
		}
	}
}
