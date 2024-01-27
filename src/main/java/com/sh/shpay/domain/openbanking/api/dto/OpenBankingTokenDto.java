package com.sh.shpay.domain.openbanking.api.dto;

import com.sh.shpay.domain.openbanking.domain.OpenBankingToken;

public class OpenBankingTokenDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Long expireMIn;
    private String userSeqNo;

    public OpenBankingTokenDto(OpenBankingToken openBankingToken) {
        this.userId = openBankingToken.getUserId();
        this.accessToken = openBankingToken.getAccessToken();
        this.refreshToken = openBankingToken.getRefreshToken();
        this.expireMIn = openBankingToken.getExpireMin();
        this.userSeqNo = openBankingToken.getUserSeqNo();
    }

}
