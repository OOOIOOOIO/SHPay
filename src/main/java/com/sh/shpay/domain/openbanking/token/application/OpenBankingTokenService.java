package com.sh.shpay.domain.openbanking.token.application;

import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser2leggedTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser3leggedTokenResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.openbanking.token.domain.three.OpenBanking3LeggedToken;
import com.sh.shpay.domain.openbanking.token.domain.three.repository.OpenBanking3LeggedTokenRepository;
import com.sh.shpay.domain.openbanking.token.domain.two.OpenBanking2LeggedToken;
import com.sh.shpay.domain.openbanking.token.domain.two.repository.OpenBanking2LeggedTokenRepository;
import com.sh.shpay.domain.users.domain.Users;
import com.sh.shpay.domain.users.domain.repository.UsersRepository;
import com.sh.shpay.global.exception.CustomException;
import com.sh.shpay.global.exception.ErrorCode;
import com.sh.shpay.global.log.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OpenBankingTokenService {

    private final OpenBanking3LeggedTokenRepository openBanking3LeggedTokenRepository;
    private final OpenBanking2LeggedTokenRepository openBanking2LeggedTokenRepository;
    private final UsersRepository userRepository;

    /**
     * 사용자 토큰 발급 요청, 3-legged
     *
     */
    @LogTrace
    public void saveOpenBanking3LeggedToken(OpenBankingUser3leggedTokenResponseDto openBankingUser3leggedTokenResponseDto, Long userId){

        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        if(openBanking3LeggedTokenRepository.existsByUsers(users)){
            throw new RuntimeException("이미 access_token이 존재합니다."); //
        }


        OpenBanking3LeggedToken openBanking3LeggedToken = OpenBanking3LeggedToken.createOpenBankingToken(
                users,
                openBankingUser3leggedTokenResponseDto.getAccess_token(),
                openBankingUser3leggedTokenResponseDto.getRefresh_token(),
                (long) openBankingUser3leggedTokenResponseDto.getExpires_in(),
                openBankingUser3leggedTokenResponseDto.getUser_seq_no()

        );

        openBanking3LeggedTokenRepository.save(openBanking3LeggedToken);

        // user_seq_no 저장
        if(!users.hasUserSeqNo()){
            users.updateUserSeqNo(openBankingUser3leggedTokenResponseDto.getUser_seq_no());

        }

    }

    /**
     * 사용자 토큰 갱신(Access Token), 3-legged
     * @param openBankingUserRefreshTokenResponseDto
     */
    @LogTrace
    public void updateTokenInfo(OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto, Long userId) {

        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        OpenBanking3LeggedToken openBanking3LeggedToken = openBanking3LeggedTokenRepository.findByUsers(users).orElseThrow(() -> new RuntimeException("토큰 정보가 존재하지 않습니다."));

        openBanking3LeggedToken.updateOpenBankingToken(openBankingUserRefreshTokenResponseDto);

    }

    /**
     * 사용자 토큰 발급 요청, 2-legged
     */
    @LogTrace
    public void saveOpenBanking2LeggedToken(OpenBankingUser2leggedTokenResponseDto openBankingUser2leggedTokenResponseDto, Long userId){

        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        if(openBanking3LeggedTokenRepository.existsByUsers(users)){
            throw new RuntimeException("이미 access_token이 존재합니다."); //
        }


        OpenBanking2LeggedToken openBanking2LeggedToken = OpenBanking2LeggedToken.createOpenBankingToken(
                users,
                openBankingUser2leggedTokenResponseDto.getAccess_token(),
                (long) openBankingUser2leggedTokenResponseDto.getExpires_in(),
                openBankingUser2leggedTokenResponseDto.getClient_use_code()

        );

        openBanking2LeggedTokenRepository.save(openBanking2LeggedToken);

    }


    /**
     * 사용자 토큰 2-legged 삭제
     * @param userId
     */
    @LogTrace
    public void deleteOpenBankingUser2leggedToken(Long userId) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        OpenBanking2LeggedToken openBanking2LeggedToken = openBanking2LeggedTokenRepository.findByUsers(users).orElseThrow(() -> new CustomException(ErrorCode.NotExistAccessTokenException));

        openBanking2LeggedTokenRepository.delete(openBanking2LeggedToken);

    }
}
