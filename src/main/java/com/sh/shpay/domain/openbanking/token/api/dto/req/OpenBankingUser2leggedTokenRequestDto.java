package com.sh.shpay.domain.openbanking.token.api.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 사용자 토큰 발급 요청, 3-legged
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingUser2leggedTokenRequestDto {
    private String client_id;
    private String client_secret;
    private String scope;
    private String grant_type; // authorization_code 고정

    @Builder
    public OpenBankingUser2leggedTokenRequestDto(String client_id, String client_secret, String scope, String grant_type) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.scope = scope;
        this.grant_type = grant_type;
    }



    public MultiValueMap<String, String> toMultiValueMap(){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("client_id",this.client_id);
        multiValueMap.add("client_secret",this.client_secret);
        multiValueMap.add("scope", this.scope);
        multiValueMap.add("grant_type",this.grant_type);

        return multiValueMap;
    }

}
