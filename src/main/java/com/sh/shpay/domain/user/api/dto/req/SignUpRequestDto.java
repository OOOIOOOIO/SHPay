package com.sh.shpay.domain.user.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {
    private String email;
    private String password;

    public SignUpRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
