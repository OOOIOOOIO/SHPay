package com.sh.shpay.domain.openbanking.token.domain.repository;

import com.sh.shpay.domain.users.domain.Users;

import java.util.Optional;

public interface OpenBankingTokenQueryRepository {


    Optional<Users> findUsersByAccessToken(String accessToken);
}
