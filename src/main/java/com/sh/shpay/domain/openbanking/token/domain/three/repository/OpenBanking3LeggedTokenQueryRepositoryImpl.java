package com.sh.shpay.domain.openbanking.token.domain.three.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.shpay.domain.openbanking.token.domain.three.QOpenBanking3LeggedToken;
import com.sh.shpay.domain.users.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sh.shpay.domain.openbanking.token.domain.three.QOpenBanking3LeggedToken.openBanking3LeggedToken;


@Repository
@RequiredArgsConstructor
public class OpenBanking3LeggedTokenQueryRepositoryImpl implements com.sh.shpay.domain.openbanking.token.domain.three.repository.OpenBanking3LeggedTokenQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Users> findUsersByAccessToken(String accessToken) {

        return Optional.ofNullable(queryFactory
                .select(openBanking3LeggedToken.users)
                .from(openBanking3LeggedToken)
                .where(openBanking3LeggedToken.accessToken.eq(accessToken))
                .fetchOne());


    }
}
