package com.sh.shpay.global.resolver.session;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoFromSessionDto {

    private Long userId;
    private String email;

    public UserInfoFromSessionDto(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
