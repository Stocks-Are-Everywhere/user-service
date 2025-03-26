package com.onseju.userservice.company.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onseju.orderservice.company.domain.Company;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {

	List<Company> findByIsuNmContainingOrIsuAbbrvContainingOrIsuEngNmContainingOrIsuSrtCdContaining(
			String isuNm,
			String isuAbbrv,
			String isuEngNm,
			String isuSrtCd
	);

	Optional<Company> findByIsuSrtCd(String isuSrtCd);

}
