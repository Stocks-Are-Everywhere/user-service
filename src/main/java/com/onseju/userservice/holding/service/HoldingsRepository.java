package com.onseju.userservice.holding.service;

import com.onseju.userservice.holding.domain.Holdings;

public interface HoldingsRepository {

	Holdings getOrDefaultByAccountIdAndCompanyCode(final Long accountId, final String companyCode);

	Holdings getByAccountIdAndCompanyCode(final Long accountId, final String companyCode);

	Holdings save(final Holdings holdings);
}
