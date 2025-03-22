package com.onseju.userservice.account.service;

import com.onseju.userservice.account.domain.Account;
import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.exception.AccountNotFoundException;
import com.onseju.userservice.account.service.dto.AccountAfterTradeParams;
import com.onseju.userservice.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.assertj.core.api.Assertions.assertThat;

class AccountServiceTest {

    AccountService accountService;
    FakeAccountRepository fakeAccountRepository = new FakeAccountRepository();

    private final Member member = Member.builder()
            .id(100L)
            .email("test@example.com")
            .username("testuser")
            .build();

    @BeforeEach
    void setUp() {
        accountService = new AccountService(fakeAccountRepository);
        member.createAccount();
        fakeAccountRepository.save(member.getAccount());
    }

    @Test
    @DisplayName("매수 요청시, account에서 예약금을 저장한다.")
    void updateAccountAfterBuyTradeSuccess() {
        // given
        Long accountId = 1L;
        AccountAfterTradeParams params = new AccountAfterTradeParams(1L, Type.BUY, BigDecimal.ONE, BigDecimal.ONE);

        // when
        accountService.updateAccountAfterTrade(params);

        // then
        Account account = fakeAccountRepository.getById(accountId);
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(100000000).subtract(BigDecimal.ONE));
        assertThat(account.getReservedBalance().abs()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    @DisplayName("매도 요청시, account에서 금액을 추가한다.")
    void updateAccountAfterSellTradeSuccess() {
        // given
        Long accountId = 1L;
        AccountAfterTradeParams params = new AccountAfterTradeParams(1L, Type.SELL, BigDecimal.ONE, BigDecimal.ONE);

        // when
        accountService.updateAccountAfterTrade(params);

        // then
        Account account = fakeAccountRepository.getById(accountId);
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(100000001));
    }

    class FakeAccountRepository implements AccountRepository {

        ConcurrentSkipListSet<Account> elements = new ConcurrentSkipListSet<>(
                Comparator.comparing(Account::getId)
        );

        @Override
        public Account getById(Long id) {
            return elements.stream()
                    .filter(value -> value.getId().equals(id))
                    .findAny()
                    .orElseThrow(AccountNotFoundException::new);
        }

        public Account save(Account account) {
            if (account.getId() != null) {
                Account old = getById(account.getId());
                elements.remove(old);
                elements.add(account);
            }
            Account savedAccount = Account.builder()
                    .id((long) elements.size() + 1)
                    .member(account.getMember())
                    .balance(account.getBalance())
                    .reservedBalance(account.getReservedBalance())
                    .build();
            elements.add(savedAccount);
            return savedAccount;
        }
    }
}