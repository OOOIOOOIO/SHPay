package com.sh.shpay.domain.user.domain;

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
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String openBankCi;
    private String userSeqNo; // user_seq_no

    // account랑 oneToMany 생각

    @Builder
    private Users(String email, String password, String openBankCi, String userSeqNo) {
        this.email = email;
        this.password = password;
        this.openBankCi = openBankCi;
        this.userSeqNo = userSeqNo;
    }

    public static Users createUser(String email, String password, String openBankCi, String userSeqNo){
        return Users.builder()
                .email(email)
                .password(password)
                .openBankCi(openBankCi)
                .userSeqNo(userSeqNo)
                .build();

    }

}
