package com.sh.shpay.domain.acconut.api.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRequestDto {
    private String accessToken;
    private String userSeqNo;

    @Builder
    public AccountRequestDto(String accessToken, String userSeqNo) {
        this.accessToken = accessToken;
        this.userSeqNo = userSeqNo;
    }
}
