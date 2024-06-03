package com.sh.shpay.domain.acconut.api.dto.req.deposit;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositUserInputRequestDto {
    private String wd_print_content; // 출금"!"계좌내역 문구
    private String tran_amt; // 이체할 돈
    private String print_content;
    private String fintech_use_num;
    private String req_client_name; // 입금 예정자 명
    private String req_client_fintech_use_num; // 입금 예정자 명
    private String req_client_num; // 입금 예정자 계좌번호
}
