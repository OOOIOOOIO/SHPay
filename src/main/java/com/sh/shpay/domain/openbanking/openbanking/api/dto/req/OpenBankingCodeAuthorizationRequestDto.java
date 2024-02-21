package com.sh.shpay.domain.openbanking.openbanking.api.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingCodeAuthorizationRequestDto {

    private String response_type;
    private String client_id;
    private String redirect_uri;
    private String scope;
    private String state;
    private int auth_type;

    @Builder
    public OpenBankingCodeAuthorizationRequestDto(String response_type, String client_id, String redirect_uri, String scope, String state, int auth_type) {
        this.response_type = response_type;
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.scope = scope;
        this.state = state;
        this.auth_type = auth_type;
    }
}
