package com.sh.shpay.domain.acconut.api.dto.req.deposit;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositRequestDto {

    private String cntr_account_type; //n  약정 계좌/계정 구분주2)  n:계좌, c:계정
    private String cntr_account_num;  //약정 계좌/계정 번호주2) 내계좌
    private String wd_pass_phrase;
    private String wd_print_content;
    private String name_check_option;
    private String tran_dtime;
    private String req_cnt;
    private List<DepositRequestList> req_list;

    @Builder
    public DepositRequestDto(String cntr_account_type, String cntr_account_num, String wd_pass_phrase, String wd_print_content, String name_check_option, String tran_dtime, String req_cnt, List<DepositRequestList> req_list) {
        this.cntr_account_type = cntr_account_type;
        this.cntr_account_num = cntr_account_num;
        this.wd_pass_phrase = wd_pass_phrase;
        this.wd_print_content = wd_print_content;
        this.name_check_option = name_check_option;
        this.tran_dtime = tran_dtime;
        this.req_cnt = req_cnt;
        this.req_list = req_list;
    }

    public void setTran_dtime(String tran_dtime) {
        this.tran_dtime = tran_dtime;
    }
}
