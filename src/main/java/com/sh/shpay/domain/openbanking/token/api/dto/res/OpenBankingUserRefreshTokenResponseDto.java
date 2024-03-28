package com.sh.shpay.domain.openbanking.token.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 토큰 갱신(Access Token), 3-legged
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingUserRefreshTokenResponseDto {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String refresh_token;
    private String scope;
    private String user_seq_no;
}
