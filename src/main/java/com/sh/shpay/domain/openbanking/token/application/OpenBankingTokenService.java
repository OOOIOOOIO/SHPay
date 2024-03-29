package com.sh.shpay.domain.openbanking.token.application;

import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser3leggedTokenResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.openbanking.token.domain.OpenBankingToken;
import com.sh.shpay.domain.openbanking.token.domain.repository.OpenBankingTokenRepository;
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
public class OpenBankingTokenService {

    private final OpenBankingTokenRepository openBankingTokenRepository;
    private final UsersRepository userRepository;
    private final OpenBankingService openBankingService;

    /**
     * 사용자 토큰 발급 요청, 3-legged
     * open banking token 저장
     *
     * 만료시간 생각해야함
     */
    public void saveOpenBankingUserToken(OpenBankingUser3leggedTokenResponseDto openBankingUser3leggedTokenResponseDto, Long userId){

        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        if(openBankingTokenRepository.existsByUsers(users)){
            throw new RuntimeException("이미 access_token이 존재합니다."); //
        }


        OpenBankingToken openBankingToken = OpenBankingToken.createOpenBankingToken(
                users,
                openBankingUser3leggedTokenResponseDto.getAccess_token(),
                openBankingUser3leggedTokenResponseDto.getRefresh_token(),
                (long) openBankingUser3leggedTokenResponseDto.getExpires_in(),
                openBankingUser3leggedTokenResponseDto.getUser_seq_no()

        );

        openBankingTokenRepository.save(openBankingToken);

        // user_seq_no 저장
        if(!users.hasUserSeqNo()){
            users.updateUserSeqNo(openBankingUser3leggedTokenResponseDto.getUser_seq_no());

        }

    }

    /**
     * 사용자 토큰 갱신(Access Token), 3-legged
     * @param openBankingUserRefreshTokenResponseDto
     */
    public void updateTokenInfo(OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto, Long userId) {

        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        OpenBankingToken openBankingToken = openBankingTokenRepository.findByUsers(users).orElseThrow(() -> new RuntimeException("토큰 정보가 존재하지 않습니다."));

        openBankingToken.updateOpenBankingToken(openBankingUserRefreshTokenResponseDto);

    }



}
