package com.sh.shpay.domain.openbanking.openbanking.dto.req;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingUserInfoRequestDto {
    private String user_seq_no;
    private String accessToken;

    @Builder
    public OpenBankingUserInfoRequestDto(String user_seq_no, String accessToken) {
        this.user_seq_no = user_seq_no;
        this.accessToken = accessToken;
    }
}
