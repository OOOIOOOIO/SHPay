package com.sh.shpay.domain.acconut.api.dto.req.deposit;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositAccountInfoDto {
    private String account_num;
    private String fintech_use_num;

    public DepositAccountInfoDto(String account_num, String fintech_use_num) {
        this.account_num = account_num;
        this.fintech_use_num = fintech_use_num;
    }
}
