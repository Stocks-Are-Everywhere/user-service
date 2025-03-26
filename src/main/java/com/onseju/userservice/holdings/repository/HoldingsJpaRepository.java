package com.onseju.userservice.holdings.repository;

import com.onseju.userservice.holdings.domain.Holdings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HoldingsJpaRepository extends JpaRepository<Holdings, Long> {
	Optional<Holdings> findByAccountIdAndCompanyCode(final Long accountId, final String companyCode);
}
