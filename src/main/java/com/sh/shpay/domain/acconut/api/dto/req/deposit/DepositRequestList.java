package com.sh.shpay.domain.acconut.api.dto.req.deposit;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositRequestList {

    private String tran_no;
    private String bank_tran_id;
    private String fintech_use_num;
    private String print_content;
    private String tran_amt;
    private String req_client_name;
    private String req_client_fintech_use_num;
    private String req_client_num;
    private String transfer_purpose;


    @Builder
    public DepositRequestList(String tran_no, String bank_tran_id, String fintech_use_num, String print_content, String tran_amt, String req_client_name, String req_client_fintech_use_num, String req_client_num, String transfer_purpose) {
        this.tran_no = tran_no;
        this.bank_tran_id = bank_tran_id;
        this.fintech_use_num = fintech_use_num;
        this.print_content = print_content;
        this.tran_amt = tran_amt;
        this.req_client_name = req_client_name;
        this.req_client_fintech_use_num = req_client_fintech_use_num;
        this.req_client_num = req_client_num;
        this.transfer_purpose = transfer_purpose;
    }


    public void setBank_tran_id(String bank_tran_id) {
        this.bank_tran_id = bank_tran_id;
    }
}
