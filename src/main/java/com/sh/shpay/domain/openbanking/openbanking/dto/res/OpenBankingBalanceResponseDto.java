package com.sh.shpay.domain.openbanking.openbanking.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingBalanceResponseDto {
    private String api_tran_id;
    private String api_tran_dtm;
    private String rsp_code;
    private String rsp_message;
    private String bank_tran_id;
    private String bank_tran_date;
    private String bank_code_tran;
    private String bank_rsp_code;
    private String bank_rsp_message;
    private String bank_name;
    private String savings_bank_name;
    private String fintech_use_num;
    private String balance_amt;
    private String available_amt;
    private String account_type;
    private String product_name;
    private String account_issue_date;
    private String maturity_date;
    private String last_tran_date;
}
