package com.sh.shpay.domain.openbanking.token.domain.three.repository;

import com.sh.shpay.domain.openbanking.token.domain.three.OpenBanking3LeggedToken;
import com.sh.shpay.domain.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenBanking3LeggedTokenRepository extends JpaRepository<OpenBanking3LeggedToken, Long> {

    Optional<OpenBanking3LeggedToken> findByUsers(Users users);

    boolean existsByUsers(Users users);

    Optional<OpenBanking3LeggedToken> findByAccessToken(String accessToken);
}
