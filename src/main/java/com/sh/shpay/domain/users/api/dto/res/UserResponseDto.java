package com.sh.shpay.domain.users.api.dto.res;

import com.sh.shpay.domain.users.domain.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private Long userId;
    private String name;
    private String email;


    public UserResponseDto(Users users) {
        this.userId = users.getUserId();
        this.name = users.getName();
        this.email = users.getEmail();
    }

}
