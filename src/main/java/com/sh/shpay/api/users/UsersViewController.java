package com.sh.shpay.api.users;

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


    @GetMapping("/signin")
    public String signInPage(){

        return "sign-in-page";
    }


    @GetMapping("/signup")
    public String signUpPage(){

        return "sign-up-page";
    }
}
