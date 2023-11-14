package com.sh.shpay.domain.openbanking.application;

import com.sh.shpay.domain.openbanking.api.dto.OpenBankingTokenDto;
import com.sh.shpay.domain.openbanking.api.dto.req.OpenBankingUserCodeRequestDto;
import com.sh.shpay.domain.openbanking.api.dto.res.OpenBankingUserTokenResponseDto;
import com.sh.shpay.domain.openbanking.domain.OpenBankingToken;
import com.sh.shpay.domain.openbanking.domain.repository.OpenBankingTokenRepository;
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
public class OpenBankingTokenService {

    private final OpenBankingTokenRepository openBankingTokenRepository;
    private final UserRepository userRepository;
    private final OpenBankingService openBankingService;

    /**
     * open banking token 저장
     */
    public void saveOpenBankingUserToken(OpenBankingUserCodeRequestDto openBankingUserCodeRequestDto){

        Users user = userRepository.findById(openBankingUserCodeRequestDto.getUserId()).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        if(openBankingTokenRepository.existsByUserId(openBankingUserCodeRequestDto.getUserId())){
            throw new RuntimeException("이미 토큰이 존재합니다.");
        }

        OpenBankingUserTokenResponseDto openBankingUserTokenResponseDto = openBankingService.requestUserToken(openBankingUserCodeRequestDto);

        OpenBankingToken openBankingToken = OpenBankingToken.createOpenBankingToken(
                openBankingUserCodeRequestDto.getUserId(),
                openBankingUserTokenResponseDto.getAccess_token(),
                openBankingUserTokenResponseDto.getRefresh_token(),
                (long) openBankingUserTokenResponseDto.getExpires_in(),
                openBankingUserTokenResponseDto.getUser_seq_no()

        );

        openBankingTokenRepository.save(openBankingToken);

        if(!user.hasUserSeqNo()){
            user.updateUserSeqNo(openBankingUserTokenResponseDto.getUser_seq_no());
        }

    }

    /**
     * open banking token 조회
     */
    public OpenBankingTokenDto getOpenBankingTokenByUserId(Long userId){
        OpenBankingToken openBankingToken = openBankingTokenRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("토큰이 존재하지 않습니다."));

        return new OpenBankingTokenDto(openBankingToken);
    }

}
