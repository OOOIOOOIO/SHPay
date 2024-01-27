package com.sh.shpay.domain.user.domain;

import com.sh.shpay.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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
    private String openBankCi; // user_ci인가? 확인
    private String userSeqNo; // user_seq_no(유저고유번호)

    // Account OneToMany 생각
    // OpenBankingToken OneToOne 생각

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

    public void updateUserSeqNo(String userSeqNo){
        this.userSeqNo = userSeqNo;
    }
    public void updateOpenBankCi(String openBankCi){
        this.openBankCi = openBankCi;
    }

    public boolean isValidPassword(String password){
        if (!this.password.equals(password)){
            return false;
        }
        return true;
    }

    public boolean hasOpenBankCi(){
        if (this.openBankCi == null || this.openBankCi.isBlank()){
            return false;
        }
        return true;
    }

    public boolean hasUserSeqNo(){
        if (this.userSeqNo == null || this.userSeqNo.isBlank()){
            return false;
        }
        return true;
    }

}
