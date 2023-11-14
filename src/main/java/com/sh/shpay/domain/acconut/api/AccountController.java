package com.sh.shpay.domain.acconut.api;

import com.sh.shpay.domain.acconut.api.dto.UserAccountDto;
import com.sh.shpay.domain.acconut.application.AccountService;
import com.sh.shpay.domain.openbanking.api.dto.OpenBankingAccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@CrossOrigin // CORS 설정. 모든 도메인, 모든 요청방식 허용
public class AccountController {

    private final AccountService accountService;

    /**
     * 계좌 조회
     * @param userId
     * @return
     */
    @GetMapping("/members/{userid}/account")
    public ResponseEntity<List<UserAccountDto>> requestAccount(@PathVariable(name = "userId") Long userId){
        List<UserAccountDto> accounts = accountService.requestAccountByUserId(userId);
        return ResponseEntity.ok().body(accounts);
    }

    /**
     * 계좌 저장
     * @param userId
     * @return
     */
    @PostMapping("/users/{userId}/account")
    public ResponseEntity<Long> saveAccounts(@PathVariable(name = "userId") Long userId){
        Long size = accountService.saveAccountList(userId);
        return ResponseEntity.ok().body(size);
    }

    /**
     * 계좌 수정(주계좌 설정)
     * @param userId
     * @param accountId
     * @return
     */
    @PutMapping("/users/{userId}/account/{accountId}")
    public ResponseEntity updateAccountType(@PathVariable(name = "userId") Long userId, @PathVariable("accountId") Long accountId) {
        accountService.updateAccountType(userId, accountId);
        return ResponseEntity.status(200).build();
    }

}
