package com.sh.shpay.domain.openbanking.api.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingAccountResponseDto {
    private String fintech_use_num;
    private String bank_name;
    private String account_num;
    private String account_num_masked;
    private String account_seq;
    private String balance_amt;


    @Builder
    public OpenBankingAccountResponseDto(String fintech_use_num, String bank_name, String account_num, String account_num_masked, String account_seq, String balance_amt) {
        this.fintech_use_num = fintech_use_num;
        this.bank_name = bank_name;
        this.account_num = account_num;
        this.account_num_masked = account_num_masked;
        this.account_seq = account_seq;
        this.balance_amt = balance_amt;
    }


}
