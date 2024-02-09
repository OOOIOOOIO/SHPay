package com.sh.shpay.api.users;

import com.sh.shpay.domain.users.api.dto.req.UserSignUpRequestDto;
import com.sh.shpay.domain.users.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersViewController {

    /**
     * 로그인 페이지
     *
     * @return
     */
    @GetMapping("/signin")
    public String signInPage() {

        return "sign-in-page";
    }

    private final UserService userService;


    /**
     * 회원가입 페이지
     *
     * @return
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
