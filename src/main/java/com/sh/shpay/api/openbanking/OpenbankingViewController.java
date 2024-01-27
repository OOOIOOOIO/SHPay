package com.sh.shpay.api.openbanking;

import com.sh.shpay.domain.openbanking.api.dto.req.OpenBankingUserCodeRequestDto;
import com.sh.shpay.domain.openbanking.application.OpenBankingService;
import com.sh.shpay.global.session.resolver.usersession.UserInfoFromSession;
import com.sh.shpay.global.session.resolver.usersession.UserInfoFromSessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/openbanking")
public class OpenbankingViewController {

    private final OpenBankingService openBankingService;

    /**
     * 토큰 신청 페이지
     * auth-page
     * client-id 넘겨줘야
     * <p>
     * cod = 처음에 blank여야 첫 화면으로 갈텐데
     */
    @GetMapping("/auth")
    public String authPage(@UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto) {

        // code, userId
//        openBankingService.requestUserToken(new OpenBankingUserCodeRequestDto("", userInfoFromSessionDto.getUserId()));

        return "auth-page";
    }


    /**
     * 계좌내역 페이지
     * balance-page
     */
    @GetMapping("/balance")
    public String balancePage(){

        return "balance-page";
    }
}
