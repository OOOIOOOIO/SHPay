package com.sh.shpay.domain.users.api;

import com.sh.shpay.domain.users.api.dto.req.UserSignInRequestDto;
import com.sh.shpay.domain.users.api.dto.req.UserSignUpRequestDto;
import com.sh.shpay.domain.users.api.dto.res.UserResponseDto;
import com.sh.shpay.domain.users.application.UsersService;
import com.sh.shpay.global.common.SessionConst;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;


    /**
     * 회원가입
     */
    @Operation(
            summary = "회원가입 API",
            description = "회원가입"
    )
    @ApiResponse(
            responseCode = "200",
            description = "회원가입에 성공하였습니다."
    )
    @LogTrace
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@ModelAttribute UserSignUpRequestDto userSignUpRequestDto){

        usersService.signUp(userSignUpRequestDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    /**
     * 로그인, session방식
     */
    @Operation(
            summary = "로그인 API",
            description = "로그인"
    )
    @ApiResponse(
            responseCode = "200",
            description = "로그인 성공하였습니다."
    )
    @LogTrace
    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> signIn(@ModelAttribute UserSignInRequestDto signInRequestDto,
                                                  HttpServletRequest request){

        UserResponseDto userResponseDto = usersService.signIn(signInRequestDto);

        // session 저장
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.COMMON_USER.name(), new UserInfoFromSessionDto(userResponseDto.getUserId(), userResponseDto.getName(), userResponseDto.getEmail()));

        UserInfoFromSessionDto userInfoFromSessionDto = (UserInfoFromSessionDto) session.getAttribute(SessionConst.COMMON_USER.name());
        log.info("userId : " + userInfoFromSessionDto.getUserId());
        log.info("email : " + userInfoFromSessionDto.getEmail());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }


}
