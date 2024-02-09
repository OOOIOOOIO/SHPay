package com.sh.shpay.domain.openbanking.token.domain.repository;

import com.sh.shpay.domain.openbanking.token.domain.OpenBankingToken;
import com.sh.shpay.domain.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenBankingTokenRepository extends JpaRepository<OpenBankingToken, Long> {

    Optional<OpenBankingToken> findByUsers(Users users);

    boolean existsByUsers(Users users);

}
