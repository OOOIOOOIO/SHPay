package com.sh.shpay.domain.openbanking.token.domain.two;

import com.sh.shpay.domain.common.BaseTimeEntity;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.users.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenBanking2LeggedToken extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;
    /**
     member 하나당 오픈뱅킹 사용자 토큰 생성된다.
     **/
    @Column(length = 3000)
    private String accessToken;
    private Long expireMin;
    @Column(unique = true)
    private String clientUseCode; // user_seq_no --> users에 있는데
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users; // oneToOne 고려

    @Builder
    private OpenBanking2LeggedToken(Users users, String accessToken, Long expireMin, String clientUseCode) {
        this.users = users;
        this.accessToken = accessToken;
        this.expireMin = expireMin;
        this.clientUseCode = clientUseCode;
    }

    public static OpenBanking2LeggedToken createOpenBankingToken(Users users, String accessToken, Long expireMin, String clientUseCode){
        return OpenBanking2LeggedToken.builder()
                .users(users)
                .accessToken(accessToken)
                .expireMin(expireMin)
                .clientUseCode(clientUseCode)
                .build();

    }





}
