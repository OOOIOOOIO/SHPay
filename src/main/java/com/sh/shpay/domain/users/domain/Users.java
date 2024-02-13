package com.sh.shpay.domain.users.domain;

import com.sh.shpay.domain.acconut.domain.Account;
import com.sh.shpay.domain.common.BaseTimeEntity;
import com.sh.shpay.domain.openbanking.token.domain.OpenBankingToken;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    @Column(unique = true)
    private String password;

    @Column(unique = true)
    private String openBankCi; // user_ci인가? 확인
    @Column(unique = true)
    private String userSeqNo; // user_seq_no(유저고유번호)

    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    List<Account> accountList = new ArrayList<>();

    // OpenBankingToken OneToOne 생각
    @OneToOne(mappedBy = "users")
    private OpenBankingToken openBankingToken;

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
    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addAccountList(Account account){

        if (account.getUsers() != null) {
            account.getUsers().getAccountList().remove(account);
        }
        account.setUsers(this);

        this.accountList.add(account);
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
