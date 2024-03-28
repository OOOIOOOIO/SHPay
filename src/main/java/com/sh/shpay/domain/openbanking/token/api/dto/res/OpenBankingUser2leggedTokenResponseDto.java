package com.sh.shpay.domain.openbanking.token.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 토큰 발급 요청, 3-legged
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingUser2leggedTokenResponseDto {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
    private String client_use_code;
}

