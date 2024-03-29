package com.sh.shpay.domain.acconut.api;

import com.sh.shpay.domain.acconut.api.dto.req.WithdrawRequestDto;
import com.sh.shpay.domain.acconut.api.dto.res.AccountListResponseDto;
import com.sh.shpay.domain.acconut.api.dto.res.TransactionListResponseDto;
import com.sh.shpay.domain.acconut.application.AccountService;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingTransferResponseDto;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.OpenbankingTokenInfoFromHeader;
import com.sh.shpay.global.resolver.token.OpenbankingTokenInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     */
    @LogTrace
    @GetMapping("/list")
    public ResponseEntity<AccountListResponseDto> requestAccountInfo(@OpenbankingTokenInfoFromHeader OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto,
                                                                 @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){
        AccountListResponseDto accountListResponseDto = accountService.requestAccountList(openbankingTokenInfoFromHeaderDto, userInfoFromSessionDto);

        return new ResponseEntity(accountListResponseDto, OK);
    }

    /**
     * 등록계좌조회 후 DB 저장(계좌 조회 API 조회 후 DB 저장)
     *
     * openAPI에서 계좌 리스트 가져옴
     */
    @LogTrace
    @PostMapping("/list")
    public ResponseEntity<Long> saveAccountList(@OpenbankingTokenInfoFromHeader OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto,
                                             @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){
        Long size = accountService.saveAccountList(openbankingTokenInfoFromHeaderDto, userInfoFromSessionDto);

        return new ResponseEntity(size, OK);
    }

    /**
     * 주계좌 설정
     */
    @LogTrace
    @PutMapping("/{accountId}")
    public ResponseEntity<String> updateAccountType(@PathVariable("accountId") Long accountId,
                                                    @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){
        accountService.updateAccountType(userInfoFromSessionDto, accountId);

        return new ResponseEntity("success", OK);
    }



    /**
     * 거래내역조회
     */
    @LogTrace
    @GetMapping("/transaction/{accountId}")
    public ResponseEntity<TransactionListResponseDto> getTransactionList(@PathVariable("accountId") Long accountId,
                                                                      @OpenbankingTokenInfoFromHeader OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto){

        TransactionListResponseDto transactionListResponseDto = accountService.requestTransactionList(openbankingTokenInfoFromHeaderDto, accountId);

        return new ResponseEntity<>(transactionListResponseDto, OK);
    }



    /**
     * 출금이제
     */
    @LogTrace
    @PostMapping("/withdraw/{accountId}")
    public OpenBankingTransferResponseDto requestWithdraw(@PathVariable("accountId") Long accountId,
                                                          @OpenbankingTokenInfoFromHeader OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto,
                                                          @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto,
                                                          @RequestBody WithdrawRequestDto withdrawRequestDto){

        log.info("================= AccountController | api/openbanking/token/request - 2-legged =================");

        OpenBankingTransferResponseDto openBankingTransferResponseDto = accountService.requestWithdraw(openbankingTokenInfoFromHeaderDto, userInfoFromSessionDto, accountId, withdrawRequestDto);

        return openBankingTransferResponseDto;

    }




}
