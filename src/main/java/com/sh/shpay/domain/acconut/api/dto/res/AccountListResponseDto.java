package com.sh.shpay.domain.acconut.api.dto.res;

import com.sh.shpay.domain.acconut.api.dto.UserAccountDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountListResponseDto {

    private List<UserAccountDto> userAccountDtoList;

    public AccountListResponseDto(List<UserAccountDto> userAccountDtoList) {
        this.userAccountDtoList = userAccountDtoList;
    }
}
