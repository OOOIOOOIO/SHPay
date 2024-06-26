package com.sh.shpay.global.resolver.token.two;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Openbanking2LeggedTokenFromHeaderDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public Openbanking2LeggedTokenFromHeaderDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
