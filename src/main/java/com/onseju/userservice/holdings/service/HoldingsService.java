package com.onseju.userservice.holdings.service;

import com.onseju.userservice.holdings.domain.Holdings;
import com.onseju.userservice.holdings.service.dto.UpdateHoldingsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoldingsService {

	private final HoldingsRepository holdingsRepository;

	public void updateHoldingsAfterTrade(final UpdateHoldingsDto params) {
		final Holdings holdings = holdingsRepository.getByAccountIdAndCompanyCode(params.accountId(), params.companyCode());
		holdings.updateHoldings(params.type(), params.price(), params.quantity());
		holdingsRepository.save(holdings);
	}
}
