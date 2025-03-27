// package com.onseju.userservice.listener;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.math.BigDecimal;
// import java.time.Instant;
// import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.TimeUnit;
//
// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
//
// import com.onseju.userservice.account.domain.Account;
// import com.onseju.userservice.account.service.AccountService;
// import com.onseju.userservice.account.service.repository.AccountRepository;
// import com.onseju.userservice.events.MatchedEvent;
// import com.onseju.userservice.events.listener.MatchedEventListener;
// import com.onseju.userservice.events.mapper.EventMapper;
// import com.onseju.userservice.member.domain.Member;
// import com.onseju.userservice.member.domain.Role;
// import com.onseju.userservice.member.service.repository.MemberRepository;
//
// @SpringBootTest
// class MatchedEventListenerTest {
//
// 	@Autowired
// 	private MatchedEventListener matchedEventListener;
//
// 	@Autowired
// 	private AccountService accountService;
//
// 	@Autowired
// 	private EventMapper eventMapper;
//
// 	@Autowired
// 	private MemberRepository memberRepository;
//
// 	@Autowired
// 	private AccountRepository accountRepository;
//
// 	private static MatchedEventListenerTest instance;
//
// 	@BeforeAll
// 	static void setUp(@Autowired MemberRepository memberRepository) {
// 		instance = new MatchedEventListenerTest();
// 		instance.memberRepository = memberRepository;
//
// 		Member member = Member.builder()
// 				.email("test@example.com")
// 				.username("testuser")
// 				.googleId("testuser")
// 				.role(Role.USER)
// 				.build();
// 		member.createAccount();
// 		instance.memberRepository.save(member);
//
// 		Member member2 = Member.builder()
// 				.email("test2@example.com")
// 				.username("testuser2")
// 				.googleId("testuser2")
// 				.role(Role.USER)
// 				.build();
// 		member2.createAccount();
// 		instance.memberRepository.save(member2);
// 	}
//
// 	@Test
// 	@DisplayName("이벤트를 전달받아 비동기로 처리한다.")
// 	void handleOrderEventShouldProcessOrder() {
// 		// given
// 		MatchedEvent matchedEvent = new MatchedEvent(
// 				"005930",
// 				1L,
// 				1L,
// 				2L,
// 				2L,
// 				BigDecimal.valueOf(100),
// 				BigDecimal.valueOf(1000),
// 				Instant.now().getEpochSecond()
// 		);
//
// 		// when
// 		CompletableFuture.runAsync(() -> matchedEventListener.handleMatchedEvent(matchedEvent))
// 				.orTimeout(2, TimeUnit.SECONDS) // 비동기 실행을 기다림
// 				.join();
//
// 		// then
// 		Assertions.assertThatCode(() -> matchedEventListener.handleMatchedEvent(matchedEvent))
// 				.doesNotThrowAnyException();
// 	}
//
// 	@Test
// 	@DisplayName("이벤트 내용을 Account에 반영한다.")
// 	void updateAccounts() {
// 		// given
// 		MatchedEvent matchedEvent = new MatchedEvent(
// 				"005930",
// 				1L,
// 				1L,
// 				2L,
// 				2L,
// 				BigDecimal.valueOf(100),
// 				BigDecimal.valueOf(1000),
// 				Instant.now().getEpochSecond()
// 		);
//
// 		// when
// 		matchedEventListener.handleMatchedEvent(matchedEvent);
//
// 		// then
// 		Account account = accountRepository.getById(1L);
// 		Account account2 = accountRepository.getById(2L);
//
// 		assertThat(account.getBalance()).isEqualTo(new BigDecimal("99900000.00"));
// 		assertThat(account2.getBalance()).isEqualTo(new BigDecimal("100100000.00"));
// 	}
// }
