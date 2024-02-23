package com.sh.shpay.domain.acconut.api;

import com.sh.shpay.domain.acconut.api.dto.UserAccountDto;
import com.sh.shpay.domain.acconut.api.dto.req.WithdrawRequestDto;
import com.sh.shpay.domain.acconut.api.dto.res.AccountListResponseDto;
import com.sh.shpay.domain.acconut.api.dto.res.TransactionListResponseDto;
import com.sh.shpay.domain.acconut.application.AccountService;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingTransferResponseDto;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeader;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openbanking/account")
@CrossOrigin // CORS 설정. 모든 도메인, 모든 요청방식 허용
public class AccountController {

    private final AccountService accountService;

    /**
     * 계좌 조회(DB에서 계좌 조회 --> 오픈뱅킹 API로 잔액조회)
     *
     *
     */
    @GetMapping("/list")
    public ResponseEntity<AccountListResponseDto> requestAccount(@TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto,
                                                                 @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){
        AccountListResponseDto accountListResponseDto = accountService.requestAccountList(tokenInfoFromHeaderDto, userInfoFromSessionDto);

        return new ResponseEntity(accountListResponseDto, OK);
    }

    /**
     * 등록계좌조회 후 DB 저장(계좌 조회 API 조회 후 DB 저장)
     *
     * openAPI에서 계좌 리스트 가져옴
     */
    @PostMapping("/list")
    public ResponseEntity<Long> saveAccounts(@TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto,
                                             @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){
        Long size = accountService.saveAccountList(tokenInfoFromHeaderDto, userInfoFromSessionDto);

        return new ResponseEntity(size, OK);
    }

    /**
     * 계좌 수정(주계좌 설정)
     */
    @PutMapping("/{accountId}")
    public ResponseEntity<String> updateAccountType(@PathVariable("accountId") Long accountId,
                                                    @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto,
                                            @TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto) {

        accountService.updateAccountType(tokenInfoFromHeaderDto, userInfoFromSessionDto, accountId);

        log.info("왜안나오지");
        return new ResponseEntity("success", OK);
    }



    /**
     * 거래내역조회
     */
    @GetMapping("/transaction/{accountId}")
    public ResponseEntity<TransactionListResponseDto> transactionList(@PathVariable("accountId") Long accountId,
                                                                      @TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto){

        TransactionListResponseDto transactionListResponseDto = accountService.requestTransactionList(tokenInfoFromHeaderDto, accountId);

        return new ResponseEntity<>(transactionListResponseDto, OK);
    }



    /**
     * 출금이제
     */
    @PostMapping("/withdraw/{accountId}")
    public OpenBankingTransferResponseDto requestWithdraw(@PathVariable("accountId") Long accountId,
                                                          @TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto,
                                                          @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto,
                                                          @RequestBody WithdrawRequestDto withdrawRequestDto){

        OpenBankingTransferResponseDto openBankingTransferResponseDto = accountService.requestWithdraw(tokenInfoFromHeaderDto, userInfoFromSessionDto, accountId, withdrawRequestDto);

        return openBankingTransferResponseDto;

    }




}
