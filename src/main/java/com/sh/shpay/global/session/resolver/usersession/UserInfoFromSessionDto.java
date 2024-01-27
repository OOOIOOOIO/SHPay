package com.sh.shpay.global.session.resolver.usersession;

import com.sh.shpay.domain.user.api.dto.UserInfoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoFromSessionDto {

    private Long userId;
    private String email;

    public UserInfoFromSessionDto(UserInfoDto userInfoDto) {
        this.userId = userInfoDto.getUserId();
        this.email = userInfoDto.getEmail();
    }
}
