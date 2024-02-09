package com.sh.shpay.domain.openbanking.openbanking.api.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum AuthType {
    INIT(0),
    SKIP(2);
    private final int authCode;

}
