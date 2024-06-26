package com.sh.shpay.domain.openbanking.token.api.dto;

import com.sh.shpay.domain.openbanking.token.domain.three.OpenBanking3LeggedToken;

public class OpenBankingTokenDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Long expireMIn;
    private String userSeqNo;

    public OpenBankingTokenDto(OpenBanking3LeggedToken openBanking3LeggedToken) {
        this.userId = openBanking3LeggedToken.getUsers().getUserId();
        this.accessToken = openBanking3LeggedToken.getAccessToken();
        this.refreshToken = openBanking3LeggedToken.getRefreshToken();
        this.expireMIn = openBanking3LeggedToken.getExpireMin();
        this.userSeqNo = openBanking3LeggedToken.getUserSeqNo();
    }

}
