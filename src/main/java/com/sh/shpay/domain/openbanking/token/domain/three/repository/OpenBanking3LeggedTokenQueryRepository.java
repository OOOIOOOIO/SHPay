package com.sh.shpay.domain.openbanking.token.domain.three.repository;

import com.sh.shpay.domain.users.domain.Users;

import java.util.Optional;

public interface OpenBanking3LeggedTokenQueryRepository {


    Optional<Users> findUsersByAccessToken(String accessToken);
}
