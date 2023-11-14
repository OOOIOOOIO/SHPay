package com.sh.shpay.domain.acconut.domain.repository;

import com.sh.shpay.domain.acconut.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long userId);

    @Query("select ac from Account as ac where ac.userId = :userId and ac.accountType = 'MAIN'")
    Optional<Account> findMainAccountByUserId(@Param("userId") Long userId);
}
