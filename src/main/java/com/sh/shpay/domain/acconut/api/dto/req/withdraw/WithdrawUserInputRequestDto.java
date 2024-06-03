package com.sh.shpay.domain.acconut.api.dto.req.withdraw;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawUserInputRequestDto {
    private String dps_print_content; // 입금계좌내역 문구
    private String wd_print_content; // 출금계좌내역 문구
    private String tran_amt; // 이체할 돈
    private String fintech_use_num;
    private String recv_client_name; // 입금 예정자 명
    private String recv_client_account_num; // 입금 예정자 계좌번호

}
