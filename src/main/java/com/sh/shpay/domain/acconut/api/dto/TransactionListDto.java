package com.sh.shpay.domain.acconut.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionListDto {

    private String tran_date;
    private String tran_time;
    private String inout_type;
    private String tran_type;
    private String print_content;
    private String tran_amt;
    private String after_balance_amt;
    private String branch_name;

}
