package com.sh.shpay.domain.acconut.domain.repository;

import com.sh.shpay.domain.acconut.domain.Account;
import com.sh.shpay.domain.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUsers(Users users);

}
