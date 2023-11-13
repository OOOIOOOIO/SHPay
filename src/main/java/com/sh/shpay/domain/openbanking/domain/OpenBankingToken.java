package com.sh.shpay.domain.openbanking.domain;

import com.sh.shpay.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(length = 1000)
    @NotNull
    private String accessToken;
    @Column(length = 1000)
    @NotNull
    private String refreshToken;
    @NotNull
    private Long expireMin;
    private String userSeqNo; // user_seq_no
    @Column(unique = true)
    private Long userId; // oneToone 고려

    @Builder
    private OpenBankingToken(Long tokenId, Long userId, String accessToken, String refreshToken, Long expireMin, String userSeqNo) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireMin = expireMin;
        this.userSeqNo = userSeqNo;
    }

    public static OpenBankingToken createOpenBankingToken(Long tokenId, Long userId, String accessToken, String refreshToken, Long expireMin, String userSeqNo){
        return OpenBankingToken.builder()
                .tokenId(tokenId)
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expireMin(expireMin)
                .userSeqNo(userSeqNo)
                .build();

    }
}
