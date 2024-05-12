package com.sh.shpay.domain.acconut.domain.repository;

import com.sh.shpay.domain.acconut.domain.Account;
import com.sh.shpay.domain.acconut.domain.AccountType;

import java.util.List;
import java.util.Optional;

public interface AccountQueryRepository {

    Optional<Account> findMainAccount(Long userId, AccountType accountType);

    List<Account> findSpecificBankAccount(Long userId, String bankName);
}
