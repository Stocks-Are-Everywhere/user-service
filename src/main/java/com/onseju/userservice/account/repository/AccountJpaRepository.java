package com.onseju.userservice.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onseju.userservice.account.domain.Account;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {
}
