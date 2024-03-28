package com.sh.shpay.global.resolver.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenbankingTokenInfoFromHeaderDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public OpenbankingTokenInfoFromHeaderDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
