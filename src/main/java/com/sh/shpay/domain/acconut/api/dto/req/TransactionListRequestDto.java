package com.sh.shpay.domain.acconut.api.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionListRequestDto {

    private String bank_tran_id;
    private String fintech_use_num;
    private String inquiry_type;
    private String inquiry_base;
    private String from_date;
    private String to_date;
    private String sort_order;
    private String tran_dtime;

    @Builder
    public TransactionListRequestDto(String bank_tran_id, String fintech_use_num, String inquiry_type, String inquiry_base, String from_date, String to_date, String sort_order) {
        this.bank_tran_id = bank_tran_id;
        this.fintech_use_num = fintech_use_num;
        this.inquiry_type = inquiry_type;
        this.inquiry_base = inquiry_base;
        this.from_date = from_date;
        this.to_date = to_date;
        this.sort_order = sort_order;
    }

    public void setTran_dtime(String tran_dtime) {
        this.tran_dtime = tran_dtime;
    }
}
