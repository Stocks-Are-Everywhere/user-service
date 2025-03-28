package com.onseju.userservice.integration.concurrent;

import com.onseju.userservice.account.domain.Account;
import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.mapper.AccountMapper;
import com.onseju.userservice.account.service.AccountService;
import com.onseju.userservice.account.service.repository.AccountRepository;
import com.onseju.userservice.holding.domain.Holdings;
import com.onseju.userservice.holding.mapper.HoldingsMapper;
import com.onseju.userservice.holding.service.HoldingsService;
import com.onseju.userservice.holding.service.repository.HoldingsRepository;
import com.onseju.userservice.member.domain.Member;
import com.onseju.userservice.member.domain.Role;
import com.onseju.userservice.member.service.repository.MemberRepository;
import com.onseju.userservice.order.BeforeTradeOrderDto;
import com.onseju.userservice.order.OrderReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderReservationServiceTest {

    private static final int THREAD_COUNT = 1000;
    private static final String COMPANY_CODE = "005930";

    @Autowired
    private OrderReservationService orderReservationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HoldingsService holdingsService;
    @Autowired
    private HoldingsMapper holdingsMapper;
    @Autowired
    private HoldingsRepository holdingsRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .email("test@example.com")
                .username("testuser")
                .googleId("testuser")
                .role(Role.USER)
                .build();
        member.createAccount();

        memberRepository.save(member);
        accountRepository.save(member.getAccount());

        holdingsRepository.save(
                Holdings.builder()
                        .companyCode(COMPANY_CODE)
                        .quantity(new BigDecimal(THREAD_COUNT))
                        .reservedQuantity(BigDecimal.ZERO)
                        .averagePrice(new BigDecimal(100))
                        .totalPurchasePrice(new BigDecimal(10000))
                        .accountId(1L)
                        .build()
        );
    }

    @Test
    void shouldReserveHoldingsConcurrentCorrectly() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        BeforeTradeOrderDto dto = new BeforeTradeOrderDto(COMPANY_CODE, "LIMIT_SELL", BigDecimal.ONE, BigDecimal.ONE, Instant.now().getEpochSecond(), 1L);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    orderReservationService.validateUserInfoForOrder(dto);
                } catch (Exception e) {
                    System.out.println("[Exception] " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Holdings holdings = holdingsRepository.getByAccountIdAndCompanyCode(1L, COMPANY_CODE);
        assertThat(holdings.getReservedQuantity().intValueExact()).isEqualTo(THREAD_COUNT);
    }

    @Test
    void shouldReserveAccountConcurrentCorrectly() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        BeforeTradeOrderDto dto = new BeforeTradeOrderDto(COMPANY_CODE, "LIMIT_BUY", BigDecimal.ONE, BigDecimal.ONE, Instant.now().getEpochSecond(), 1L);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    orderReservationService.validateUserInfoForOrder(dto);
                } catch (Exception e) {
                    System.out.println("[Exception] " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Account account = accountRepository.getByMemberId(1L);
        assertThat(account.getReservedBalance().intValueExact()).isEqualTo(THREAD_COUNT);
    }

    @Test
    void accountService() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        BeforeTradeOrderDto dto = new BeforeTradeOrderDto(COMPANY_CODE, "LIMIT_BUY", BigDecimal.ONE, BigDecimal.ONE, Instant.now().getEpochSecond(), 1L);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    accountService.reserve(accountMapper.toBeforeTradeAccountDto(dto, Type.BUY));
                } catch (Exception e) {
                    System.out.println("[Exception] " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Account account = accountRepository.getByMemberId(1L);
        assertThat(account.getReservedBalance().intValueExact()).isEqualTo(THREAD_COUNT);
    }
}