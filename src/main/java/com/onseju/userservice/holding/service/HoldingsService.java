package com.onseju.userservice.holding.service;

import com.onseju.userservice.holding.domain.Holdings;
import com.onseju.userservice.holding.service.dto.UpdateHoldingsDto;
import com.onseju.userservice.order.controller.request.OrderValidationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HoldingsService {

	private final HoldingsRepository holdingsRepository;

	@Transactional
	public void updateHoldingsAfterTrade(final UpdateHoldingsDto params) {
		final Holdings holdings = holdingsRepository.getOrDefaultByAccountIdAndCompanyCode(params.accountId(), params.companyCode());
		holdings.updateHoldings(params.type(), params.price(), params.quantity());
		holdingsRepository.save(holdings);
	}

	@Transactional
	public void reserveHoldings(final OrderValidationRequest request, final Long accountId) {
		if (request.type().isSell()) {
			final Holdings holdings = holdingsRepository.getByAccountIdAndCompanyCode(accountId, request.companyCode());
			holdings.validateExistHoldings();
			holdings.validateEnoughHoldings(request.quantity());
			holdings.reserveOrder(request.quantity());
		}
	}
}
