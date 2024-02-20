package com.sh.shpay.domain.acconut.api.dto.res;

import com.sh.shpay.domain.acconut.api.dto.TransactionListDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionListResponseDto {

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

    private List<TransactionListDto> res_list;


}
