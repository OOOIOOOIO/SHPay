package com.sh.shpay.domain.openbanking.api.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpenBankingUserCodeRequestDto {

    private String code;
    private Long userId; // userId가 있어야하나 싶다

    public OpenBankingUserCodeRequestDto(String code, Long userId) {
        this.code = code;
        this.userId = userId;
    }



}
