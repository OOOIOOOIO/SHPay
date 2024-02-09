package com.sh.shpay.domain.openbanking.openbanking.api.dto;

import com.sh.shpay.domain.openbanking.token.domain.OpenBankingToken;

public class OpenBankingTokenDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Long expireMIn;
    private String userSeqNo;

    public OpenBankingTokenDto(OpenBankingToken openBankingToken) {
        this.userId = openBankingToken.getUsers().getUserId();
        this.accessToken = openBankingToken.getAccessToken();
        this.refreshToken = openBankingToken.getRefreshToken();
        this.expireMIn = openBankingToken.getExpireMin();
        this.userSeqNo = openBankingToken.getUserSeqNo();
    }

}
