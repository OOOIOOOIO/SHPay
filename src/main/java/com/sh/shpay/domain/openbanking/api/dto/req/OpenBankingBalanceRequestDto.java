package com.sh.shpay.domain.openbanking.api.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingBalanceRequestDto {
    private String accessToken;
    private String bank_tran_id;
    private String fintech_use_num;
    private String tran_dtime;

    @Builder
    public OpenBankingBalanceRequestDto(String accessToken, String bank_tran_id, String fintech_use_num, String tran_dtime) {
        this.accessToken = accessToken;
        this.bank_tran_id = bank_tran_id;
        this.fintech_use_num = fintech_use_num;
        this.tran_dtime = tran_dtime;
    }
}
