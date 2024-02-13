package com.sh.shpay.domain.users.application;

import com.sh.shpay.domain.openbanking.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.openbanking.token.domain.repository.OpenBankingTokenRepository;
import com.sh.shpay.domain.users.api.dto.req.UserSignInRequestDto;
import com.sh.shpay.domain.users.api.dto.req.UserSignUpRequestDto;
import com.sh.shpay.domain.users.api.dto.res.UserResponseDto;
import com.sh.shpay.domain.users.domain.Users;
import com.sh.shpay.domain.users.domain.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {

    private final UsersRepository userRepository;
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
     * 로그인, session 방식
     */
    public UserResponseDto signIn(UserSignInRequestDto signInRequestDto){

        Users users = userRepository.findByEmail(signInRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        boolean validPassword = users.isValidPassword(signInRequestDto.getPassword());

        if(!validPassword){
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }



        return new UserResponseDto(users);

    }


    /**
     * 유저 찾기
     */
    public void updateUser(Long userId){

    }

    /**
     * OpenBanking에서 ci 값 가져와서 저장
     */
    public void getUserCI(){

    }





}
