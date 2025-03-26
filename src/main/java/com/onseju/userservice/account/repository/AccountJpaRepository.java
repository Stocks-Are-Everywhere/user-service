package com.onseju.userservice.account.repository;

import com.onseju.userservice.account.domain.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

	@Lock(LockModeType.PESSIMISTIC_READ)
	Optional<Account> findById(Long id);

}

