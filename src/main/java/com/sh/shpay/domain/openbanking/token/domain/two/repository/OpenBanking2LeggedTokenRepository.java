package com.sh.shpay.domain.openbanking.token.domain.two.repository;

import com.sh.shpay.domain.openbanking.token.domain.two.OpenBanking2LeggedToken;
import com.sh.shpay.domain.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenBanking2LeggedTokenRepository extends JpaRepository<OpenBanking2LeggedToken, Long> {

    Optional<OpenBanking2LeggedToken> findByUsers(Users users);

    boolean existsByUsers(Users users);

    Optional<OpenBanking2LeggedToken> findByAccessToken(String accessToken);
}
