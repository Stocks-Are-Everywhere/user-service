package com.onseju.userservice.account.repository;

import com.onseju.userservice.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByMemberId(Long memberId);
}
