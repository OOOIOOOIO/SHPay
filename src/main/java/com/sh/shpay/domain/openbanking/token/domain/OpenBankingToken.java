package com.sh.shpay.domain.openbanking.token.domain;

import com.sh.shpay.domain.common.BaseTimeEntity;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.users.domain.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBankingToken extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;
    /**
     member 하나당 오픈뱅킹 사용자 토큰 생성된다.
     **/
    @Column(length = 3000)
    @NotNull
    private String accessToken;
    @Column(length = 3000)
    @NotNull
    private String refreshToken;
    @NotNull
    private Long expireMin;
    @Column(unique = true)
    private String userSeqNo; // user_seq_no --> users에 있는데
    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "userId")
    private Users users; // oneToOne 고려

    @Builder
    private OpenBankingToken(Users users, String accessToken, String refreshToken, Long expireMin, String userSeqNo) {
        this.users = users;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireMin = expireMin;
        this.userSeqNo = userSeqNo;
    }

    public static OpenBankingToken createOpenBankingToken(Users users, String accessToken, String refreshToken, Long expireMin, String userSeqNo){
        return OpenBankingToken.builder()
                .users(users)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expireMin(expireMin)
                .userSeqNo(userSeqNo)
                .build();

    }


    public void updateOpenBankingToken(OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto) {
        this.accessToken = openBankingUserRefreshTokenResponseDto.getAccess_token();
        this.expireMin = openBankingUserRefreshTokenResponseDto.getExpires_in();
        this.refreshToken = openBankingUserRefreshTokenResponseDto.getRefresh_token();

    }
}
