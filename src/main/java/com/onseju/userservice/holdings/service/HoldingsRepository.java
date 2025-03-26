package com.onseju.userservice.holdings.service;

import com.onseju.userservice.holdings.domain.Holdings;

public interface HoldingsRepository {

	Holdings getByAccountIdAndCompanyCode(final Long accountId, final String companyCode);

	Holdings save(final Holdings holdings);
}
