package com.sh.shpay.domain.acconut.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.shpay.domain.acconut.domain.Account;
import com.sh.shpay.domain.acconut.domain.AccountType;
import com.sh.shpay.domain.acconut.domain.QAccount;
import com.sh.shpay.domain.users.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sh.shpay.domain.acconut.domain.QAccount.account;

@Repository
@RequiredArgsConstructor
public class AccountQueryRepositoryImpl implements AccountQueryRepository{


    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Account> findMainAccountByUsers(Long userId, AccountType accountType) {

        return Optional.ofNullable(
                queryFactory.select(account)
                        .from(account)
                        .where(account.users.userId.eq(userId), account.accountType.eq(accountType))
                        .fetchOne()

        );

    }
}
