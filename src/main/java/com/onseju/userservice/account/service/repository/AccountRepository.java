package com.onseju.userservice.account.service.repository;

import org.springframework.data.jpa.repository.Lock;

import com.onseju.userservice.account.domain.Account;

import jakarta.persistence.LockModeType;

public interface AccountRepository {

	@Lock(LockModeType.PESSIMISTIC_READ)
	Account getById(final Long id);

	void save(final Account account);

	@Lock(LockModeType.PESSIMISTIC_READ)
	Account getByMemberId(final Long memberId);
}
