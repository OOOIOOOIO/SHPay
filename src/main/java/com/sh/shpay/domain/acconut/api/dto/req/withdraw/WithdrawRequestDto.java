package com.sh.shpay.domain.acconut.api.dto.req.withdraw;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawRequestDto {
    private String bank_tran_id; //M202111920+U+랜덤숫자 9 자리
    private String cntr_account_type; //n  약정 계좌/계정 구분주2)  n:계좌, c:계정
    private String cntr_account_num;  //약정 계좌/계정 번호주2) 내계좌
    private String dps_print_content; //입금계좌인자내역
    private String fintech_use_num;   //출금계좌핀테크이용번호
    private String wd_print_content;
    private String tran_amt;   //거래금액
    private String tran_dtime;
    private String req_client_name;
    private String req_client_fintech_use_num;
//    private String req_client_bank_code;
//    private String req_client_account_num; //내계좌 -> fintech_use_num
    private String req_client_num; //임의값
    private String transfer_purpose; //TR(송금) / ST(결제)
    private String recv_client_name;
    private String recv_client_bank_code;
    private String recv_client_account_num;

    @Builder
    public WithdrawRequestDto(String bank_tran_id, String cntr_account_type, String cntr_account_num, String dps_print_content, String fintech_use_num, String wd_print_content, String tran_amt, String req_client_name, String req_client_fintech_use_num, String req_client_num, String transfer_purpose, String recv_client_name, String recv_client_bank_code, String recv_client_account_num) {
        this.bank_tran_id = bank_tran_id;
        this.cntr_account_type = cntr_account_type;
        this.cntr_account_num = cntr_account_num;
        this.dps_print_content = dps_print_content;
        this.fintech_use_num = fintech_use_num;
        this.wd_print_content = wd_print_content;
        this.tran_amt = tran_amt;
        this.req_client_name = req_client_name;
        this.req_client_fintech_use_num = req_client_fintech_use_num;
        this.req_client_num = req_client_num;
        this.transfer_purpose = transfer_purpose;
        this.recv_client_name = recv_client_name;
        this.recv_client_bank_code = recv_client_bank_code;
        this.recv_client_account_num = recv_client_account_num;
    }

    public void setTran_dtime(String tran_dtime) {
        this.tran_dtime = tran_dtime;
    }

}
