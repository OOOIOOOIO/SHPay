package com.sh.shpay.domain.openbanking.openbanking.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingSearchAccountRequestDto {

    private String accessToken;
    private String user_seq_no;
    private String include_cancel_yn;
    private String sort_order;

    @Builder
    public OpenBankingSearchAccountRequestDto(String accessToken, String user_seq_no, String include_cancel_yn, String sort_order) {
        this.accessToken = accessToken;
        this.user_seq_no = user_seq_no;
        this.include_cancel_yn = include_cancel_yn;
        this.sort_order = sort_order;
    }
}
