package com.sh.shpay.domain.acconut.api;

import com.sh.shpay.domain.acconut.api.dto.req.deposit.DepositRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.deposit.DepositUserInputRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.withdraw.WithdrawUserInputRequestDto;
import com.sh.shpay.domain.acconut.api.dto.res.AccountListResponseDto;
import com.sh.shpay.domain.acconut.api.dto.res.TransactionListResponseDto;
import com.sh.shpay.domain.acconut.api.dto.res.deposit.DepositResponseDto;
import com.sh.shpay.domain.acconut.application.AccountService;
import com.sh.shpay.domain.acconut.api.dto.res.withdraw.WithdrawResponseDto;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeader;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeaderDto;
import com.sh.shpay.global.resolver.token.two.Openbanking2LeggedTokenFromHeader;
import com.sh.shpay.global.resolver.token.two.Openbanking2LeggedTokenFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Tag(name = "Openbanking - Account", description = "Openbanking Account API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openbanking/account")
@CrossOrigin // CORS 설정. 모든 도메인, 모든 요청방식 허용
public class AccountController {

    private final AccountService accountService;

    /**
     * 계좌 조회(DB에서 계좌 조회 & 오픈뱅킹 API로 잔액조회)
     *
     */
    @Operation(
            summary = "계좌 리스트 조회 API",
            description = "Openbanking Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "계좌 리스트 조회에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("/list")
    public ResponseEntity<AccountListResponseDto> requestAccountInfo(@Openbanking3LeggedTokenFromHeader Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto,
                                                                 @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){
        AccountListResponseDto accountListResponseDto = accountService.requestAccountList(openbanking3LeggedTokenFromHeaderDto, userInfoFromSessionDto);

        return new ResponseEntity(accountListResponseDto, OK);
    }


    /**
     * 계좌 등록 후 등록한 계좌조회 후 DB 저장(계좌 조회 API 조회 후 DB 저장)
     * 오픈뱅킹에 계좌 등록할 때
     * 새로고침 누를 때
     *
     * openAPI에서 계좌 리스트 가져옴
     */
    @Operation(
            summary = "계좌 DB 저장(새로고침) API",
            description = "Openbanking Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "계좌를 DB에 저장했습니다."
    )
    @LogTrace
    @PostMapping("/list")
    public ResponseEntity<Long> saveAccountList(@Openbanking3LeggedTokenFromHeader Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto,
                                             @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){
        Long size = accountService.saveAccountList(openbanking3LeggedTokenFromHeaderDto, userInfoFromSessionDto);

        return new ResponseEntity(size, OK);
    }

    /**
     * 주계좌 설정
     */
    @Operation(
            summary = "주계좌 설정 API",
            description = "Openbanking Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "주계좌 설정에 성공하였습니다."
    )
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
    @Operation(
            summary = "거래내역 조회 API",
            description = "Openbanking Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "거래내역 조회에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("/transaction/{accountId}")
    public ResponseEntity<TransactionListResponseDto> getTransactionList(@PathVariable("accountId") Long accountId,
                                                                      @Openbanking3LeggedTokenFromHeader Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto){

        TransactionListResponseDto transactionListResponseDto = accountService.requestTransactionList(openbanking3LeggedTokenFromHeaderDto, accountId);

        return new ResponseEntity<>(transactionListResponseDto, OK);
    }



    /**
     * 출금이제
     */
    @Operation(
            summary = "출금이체 API",
            description = "Openbanking Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "출금이체에 성공하였습니다."
    )
    @LogTrace
    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<WithdrawResponseDto> requestWithdraw(@PathVariable("accountId") Long accountId,
                                               @Openbanking2LeggedTokenFromHeader Openbanking2LeggedTokenFromHeaderDto openbanking2LeggedTokenFromHeaderDto,
                                               @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto,
                                               @RequestBody WithdrawUserInputRequestDto withdrawUserInputRequestDto){

        WithdrawResponseDto withdrawResponseDto = accountService.requestWithdraw(openbanking2LeggedTokenFromHeaderDto, userInfoFromSessionDto, accountId, withdrawUserInputRequestDto);

        return new ResponseEntity<>(withdrawResponseDto, OK);

    }

    /**
     * 입금이체
     */
    @Operation(
            summary = "입급이체 API",
            description = "Openbanking Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "입금이체에 성공하였습니다."
    )
    @LogTrace
    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<DepositResponseDto> requestDeposit(@PathVariable("accountId") Long accountId,
                                              @Openbanking2LeggedTokenFromHeader Openbanking2LeggedTokenFromHeaderDto openbanking2LeggedTokenFromHeaderDto,
                                              @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto,
                                              @RequestBody DepositUserInputRequestDto depositUserInputRequestDto){

        DepositResponseDto depositResponseDto = accountService.requestDeposit(openbanking2LeggedTokenFromHeaderDto, userInfoFromSessionDto, accountId, depositUserInputRequestDto);

        return new ResponseEntity<>(depositResponseDto, OK);

    }





}
