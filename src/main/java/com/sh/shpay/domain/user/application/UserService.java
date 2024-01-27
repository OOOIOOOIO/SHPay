package com.sh.shpay.domain.user.application;

import com.sh.shpay.domain.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.openbanking.domain.repository.OpenBankingTokenRepository;
import com.sh.shpay.domain.user.api.dto.req.UserSignInRequestDto;
import com.sh.shpay.domain.user.api.dto.req.UserSignUpRequestDto;
import com.sh.shpay.domain.user.api.dto.res.UserResponseDto;
import com.sh.shpay.domain.user.domain.Users;
import com.sh.shpay.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final OpenBankingTokenRepository openBankingTokenRepository;
    private final OpenBankingService openBankingService;

    /**
     * 회원가입
     */
    public void signUp(UserSignUpRequestDto userSignUpRequestDto){
        Users user = Users.createUser(userSignUpRequestDto.getEmail(),
                userSignUpRequestDto.getPassword(),
                null,
                null);


        Users saveUser = userRepository.save(user);

    }


    /**
     * 로그인
     */
    public UserResponseDto signIn(UserSignInRequestDto signInRequestDto){
        Users users = userRepository.findByEmail(signInRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        boolean validPassword = users.isValidPassword(signInRequestDto.getPassword());

        if(!validPassword){
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        /**
         * 여기서 JWT 아니면 뭐 쓱싹쓱싹 하자구
         * Session이나, 일단 틀부터 짜자
         */
        return new UserResponseDto(users);

    }


    /**
     * 유저 찾기
     */
    public void findUser(){

    }

    /**
     * OpenBanking에서 ci 값 가져와서 저장
     */
    public void getUserCI(){

    }





}
