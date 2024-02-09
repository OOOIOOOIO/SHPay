package com.sh.shpay.domain.acconut.api.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalanceRequestDto {

    private String accessToken;
    private Long userId;
    private String fintechUseNum;
    private String bankTranId; // 추가함 -> tranId

    @Builder
    public BalanceRequestDto(String accessToken, Long userId, String fintechUseNum, String bankTranId) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.fintechUseNum = fintechUseNum;
        this.bankTranId = bankTranId;
    }
}
