package com.sh.shpay.view.openbanking;

import com.sh.shpay.domain.acconut.api.dto.res.AccountListResponseDto;
import com.sh.shpay.domain.acconut.application.AccountService;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.req.OpenBankingCodeAuthorizationRequestDto;
import com.sh.shpay.domain.openbanking.openbanking.application.OpenBankingService;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeader;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/openbanking")
public class OpenbankingViewController {

    private final OpenBankingService openBankingService;
    private final AccountService accountService;

    /**
     * 토큰 신청 페이지
     * 사용자 AuthCode 발급 요청 -> 사용자 토큰 발급 요청으로 넘어감(callback url)
     */
    @GetMapping("/auth")
    public String authPage(Model model) {

        OpenBankingCodeAuthorizationRequestDto openBankingCodeAuthorizationRequestDto = openBankingService.requestAuthorization();
        model.addAttribute("requestInfo", openBankingCodeAuthorizationRequestDto);


        return "auth-page";
    }



    /**
     * 계좌내역 페이지
     * balance-page
     */
    @GetMapping("/balance")
    public String balancePage(Model model,
                              @TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto,
                              @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){

        AccountListResponseDto accountListResponseDto = accountService.requestAccountList(tokenInfoFromHeaderDto, userInfoFromSessionDto);
        model.addAttribute("accountList", accountListResponseDto);

        return "balance-page";
    }
}
