package com.sh.shpay.domain.openbanking.domain.repository;

import com.sh.shpay.domain.openbanking.domain.OpenBankingToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenBankingTokenRepository extends JpaRepository<OpenBankingToken, Long> {

    Optional<OpenBankingToken> findByUserId(Long userId);
}
