package com.sh.shpay.domain.acconut.domain.repository;

import com.sh.shpay.domain.acconut.domain.Account;
import com.sh.shpay.domain.acconut.domain.AccountType;
import com.sh.shpay.domain.users.domain.Users;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountQueryRepository {

    Optional<Account> findMainAccountByUsers(Long userId, AccountType accountType);
}
