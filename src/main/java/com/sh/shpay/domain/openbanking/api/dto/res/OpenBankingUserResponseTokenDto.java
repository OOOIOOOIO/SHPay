package com.sh.shpay.domain.openbanking.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 인증 후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingUserResponseTokenDto {
    private String rsp_code;
    private String rsp_message;
    private String access_token;
    private String token_type;
    private int expires_in;
    private String refresh_token;
    private String scope;
    private String user_seq_no;
}


/**
 * 인증하기 -> 핸드폰 인증 -> ARS 인증 ->
 * 결과 : code=jtTHK1hHFTU1uwS17UPyJ6x4nSEjkp&scope=inquiry%20login%20transfer&state=12345678901234567890123456789012(코드만 나오네~)
 */