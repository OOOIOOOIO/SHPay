package com.sh.shpay.domain.users.api;

import com.sh.shpay.domain.users.api.dto.req.UserSignInRequestDto;
import com.sh.shpay.domain.users.api.dto.req.UserSignUpRequestDto;
import com.sh.shpay.domain.users.api.dto.res.UserResponseDto;
import com.sh.shpay.domain.users.application.UsersService;
import com.sh.shpay.global.common.SessionConst;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;


    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@ModelAttribute UserSignUpRequestDto userSignUpRequestDto){

        log.info("================= UsersController | api/users/signup =================");

        usersService.signUp(userSignUpRequestDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    /**
     * 로그인, session방식
     */
    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> signIn(@ModelAttribute UserSignInRequestDto signInRequestDto,
                                                  HttpServletRequest request){
        log.info("================= UsersController | api/users/signin =================");

        UserResponseDto userResponseDto = usersService.signIn(signInRequestDto);

        // session 저장
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.COMMON_USER.name(), new UserInfoFromSessionDto(userResponseDto.getUserId(), userResponseDto.getName(), userResponseDto.getEmail()));

        UserInfoFromSessionDto userInfoFromSessionDto = (UserInfoFromSessionDto) session.getAttribute(SessionConst.COMMON_USER.name());
        log.info("==== session 저장 성공 ====");
        log.info("userId : " + userInfoFromSessionDto.getEmail());
        log.info("userId : " + userInfoFromSessionDto.getUserId());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }


    /**
     * 유저 찾기
     */
    @GetMapping("/find/user")
    public void findUser(){

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
