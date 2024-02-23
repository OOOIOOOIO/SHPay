package com.sh.shpay.view.users;

import com.sh.shpay.domain.users.application.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersViewController {

    /**
     * 로그인 페이지
     *
     */
    @GetMapping("/signin")
    public String signInPage() {

        return "sign-in-page";
    }

    private final UsersService usersService;


    /**
     * 회원가입 페이지
     *
     */
    @GetMapping("/signup")
    public String signUpPage() {

        return "sign-up-page";
    }


    /**
     * 보유 계좌 페이지
     *
     */
    @GetMapping("/balance")
    public String balancePage(){

        return "balance-page";
    }
}
