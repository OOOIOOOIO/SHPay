package com.sh.shpay.domain.user.api;

import com.sh.shpay.domain.user.api.dto.req.SignInRequestDto;
import com.sh.shpay.domain.user.api.dto.req.SignUpRequestDto;
import com.sh.shpay.domain.user.api.dto.res.UserResponseDto;
import com.sh.shpay.domain.user.application.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        userService.signUp(signUpRequestDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    /**
     * 로그인
     */
    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto){
        UserResponseDto userResponseDto = userService.signIn(signInRequestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }


    /**
     * 유저 찾기
     */
    @GetMapping("/find/user")
    public void findUser(){

        /**
         * jwt 사용할거면 거서 꺼내자
         */
    }

    /**
     * OpenBanking에서 ci 값 가져와서 저장
     */
    @GetMapping("/find/ci")
    public void getUserCI(){

        /**
         * jwt 사용할거면 거서 꺼내자
         */
    }


}
