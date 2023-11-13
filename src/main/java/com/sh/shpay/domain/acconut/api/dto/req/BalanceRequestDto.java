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
    private String code; // 추가함

    @Builder
    public BalanceRequestDto(String accessToken, Long userId, String fintechUseNum, String code) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.fintechUseNum = fintechUseNum;
        this.code = code;
    }
}
