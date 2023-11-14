package com.sh.shpay.domain.acconut.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccountDto {
    private Long userAccountId;
    private Long userId;
    private String fintechUseNum;
    private String bankName;
    private String accountNum;
    private String bankCode;
    private String accountSeq;
    private String balanceAmt;


    @Builder
    public UserAccountDto(Long userAccountId, Long userId, String fintechUseNum, String bankName, String accountNum, String bankCode, String accountSeq, String balanceAmt) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.fintechUseNum = fintechUseNum;
        this.bankName = bankName;
        this.accountNum = accountNum;
        this.bankCode = bankCode;
        this.accountSeq = accountSeq;
        this.balanceAmt = balanceAmt;
    }
}
