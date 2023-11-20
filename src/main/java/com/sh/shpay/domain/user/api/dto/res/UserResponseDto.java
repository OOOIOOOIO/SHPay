package com.sh.shpay.domain.user.api.dto.res;

import com.sh.shpay.domain.user.domain.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private Long userId;
    private String email;

    private String userSeqNo;

    public UserResponseDto(Users users) {
        this.userId = users.getUserId();
        this.email = users.getEmail();
        this.userSeqNo = users.getUserSeqNo();
    }

}
