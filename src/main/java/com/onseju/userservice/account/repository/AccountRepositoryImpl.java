package com.onseju.userservice.account.repository;

import com.onseju.userservice.account.domain.Account;
import com.onseju.userservice.account.service.AccountRepository;
import com.onseju.userservice.account.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

	private final AccountJpaRepository accountJpaRepository;

	@Override
	public Account getById(final Long id) {
		return accountJpaRepository.findById(id)
				.orElseThrow(AccountNotFoundException::new);
	}
}
