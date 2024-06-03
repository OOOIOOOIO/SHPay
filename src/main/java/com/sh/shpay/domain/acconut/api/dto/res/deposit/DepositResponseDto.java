package com.sh.shpay.domain.acconut.api.dto.res.deposit;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositResponseDto {

    private String api_tran_id;
    private String api_tran_dtm;
    private String rsp_code;
    private String rsp_message;
    private String wd_bank_code_std;
    private String wd_bank_code_sub;
    private String wd_bank_name;
    private String wd_account_num_masked;
    private String wd_print_content;
    private String wd_account_holder_name;
    private String res_cnt;
    private List<DepositResponseList> res_list;



}
