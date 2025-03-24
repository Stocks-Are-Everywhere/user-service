package com.onseju.userservice.account.service;

import com.onseju.userservice.account.domain.Account;

public interface AccountRepository {
	Account getById(final Long id);
	Account getByMemberId(final Long memberId);
}
