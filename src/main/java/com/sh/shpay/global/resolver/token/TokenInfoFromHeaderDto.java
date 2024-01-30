package com.sh.shpay.global.resolver.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoFromHeaderDto {
    private String email;

    public UserInfoFromHeaderDto(String email) {
        this.email = email;
    }
}
