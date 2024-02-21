package com.sh.shpay.global.resolver.session;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class UserInfoFromSessionDto implements Serializable {

    private Long userId;
    private String name;
    private String email;

    public UserInfoFromSessionDto(Long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
