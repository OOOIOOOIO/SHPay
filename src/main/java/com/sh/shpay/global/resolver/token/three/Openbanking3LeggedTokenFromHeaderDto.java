package com.sh.shpay.global.resolver.token.three;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Openbanking3LeggedTokenFromHeaderDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public Openbanking3LeggedTokenFromHeaderDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
