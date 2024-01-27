package com.sh.shpay.domain.user.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {

    private Long userId;
    private String email;

    public UserInfoDto(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
