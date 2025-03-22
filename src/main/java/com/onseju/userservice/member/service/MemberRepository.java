package com.onseju.userservice.member.service;

import com.onseju.userservice.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {

	Optional<Member> findByEmail(final String email);
	Member save(final Member member);
}
