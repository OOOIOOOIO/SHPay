package com.sh.shpay.domain.openbanking.token.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.shpay.domain.users.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OpenBankingTokenQueryRepositoryImpl implements OpenBankingTokenQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Users> findUsersByAccessToken(String accessToken) {


        return Optional.empty();
    }
}
