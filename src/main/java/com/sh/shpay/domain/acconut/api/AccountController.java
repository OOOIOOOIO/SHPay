package com.sh.shpay.domain.acconut.api;

import com.sh.shpay.domain.acconut.api.dto.UserAccountDto;
import com.sh.shpay.domain.acconut.application.AccountService;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeader;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openbanking/account")
@CrossOrigin // CORS 설정. 모든 도메인, 모든 요청방식 허용
public class AccountController {

    private final AccountService accountService;

    /**
     * 계좌 조회(DB에서 조회)
     *
     * userId는 user_seq_no로 db에서 꺼내기. 아래 다 마찬가지
     */
    @GetMapping("/account")
    public ResponseEntity<List<UserAccountDto>> requestAccount(@TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto,
                                                               @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){
        List<UserAccountDto> accounts = accountService.requestAccountList(tokenInfoFromHeaderDto);

        return ResponseEntity.ok().body(accounts);
    }

    /**
     * 등록계좌조회 후 DB 저장(계좌 조회 API 조회 후 DB 저장)
     *
     * openAPI에서 계좌 리스트 가져옴
     */
    @PostMapping("/account")
    public ResponseEntity<Long> saveAccounts(@TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto){
        Long size = accountService.saveAccountList(tokenInfoFromHeaderDto);

        return ResponseEntity.ok().body(size);
    }

    /**
     * 계좌 수정(주계좌 설정)
     */
    @PutMapping("/account/{accountId}")
    public ResponseEntity updateAccountType(@PathVariable("accountId") Long accountId,
                                            @TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto) {
        accountService.updateAccountType(tokenInfoFromHeaderDto, accountId);

        return ResponseEntity.ok().build();
    }


    /**
     * 잔액조회
     * 계좌 정보(잔액 등등) 조회
     */


    /**
     * 거래내역조회
     */


    /**
     * 출금이제
     */


    /**
     * 입금이체
     */


    /**
     * 사용자 정보(ci, 계좌 리스트) 조회
     */


}
