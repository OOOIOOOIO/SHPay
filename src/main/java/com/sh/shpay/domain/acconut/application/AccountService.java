package com.sh.shpay.domain.acconut.application;

import com.sh.shpay.domain.acconut.domain.repository.AccountRepository;
import com.sh.shpay.domain.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.openbanking.domain.repository.TokenRepository;
import com.sh.shpay.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository; //token, openbanking 합치기
    private final OpenBankingService openBankService;


    /**
     * 계좌 조회
     */


    /**
     * 계좌 저장
     */

    /**
     * 주계좌 설정
     */
}
