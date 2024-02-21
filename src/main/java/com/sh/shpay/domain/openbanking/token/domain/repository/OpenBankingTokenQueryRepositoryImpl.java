package com.sh.shpay.domain.openbanking.token.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.shpay.domain.openbanking.token.domain.QOpenBankingToken;
import com.sh.shpay.domain.users.domain.QUsers;
import com.sh.shpay.domain.users.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sh.shpay.domain.openbanking.token.domain.QOpenBankingToken.openBankingToken;
import static com.sh.shpay.domain.users.domain.QUsers.users;

@Repository
@RequiredArgsConstructor
public class OpenBankingTokenQueryRepositoryImpl implements OpenBankingTokenQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Users> findUsersByAccessToken(String accessToken) {

        return Optional.ofNullable(queryFactory
                .select(openBankingToken.users)
                .from(openBankingToken)
                .where(openBankingToken.accessToken.eq(accessToken))
                .fetchOne());


    }
}
