package com.sh.shpay.domain.acconut.domain;

import com.sh.shpay.domain.common.BaseTimeEntity;
import com.sh.shpay.domain.users.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;
    @Column(unique = true)
    private String fintechUseNum;
    private String bankName;
    private String accountNum;
    private String bankCode;
    private String accountSeq;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String holderName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users; // ManyToOne 생각

    @Builder
    private Account(String fintechUseNum, String bankName, String accountNum, String bankCode, String accountSeq, AccountType accountType, String holderName, Users users) {
        this.fintechUseNum = fintechUseNum;
        this.bankName = bankName;
        this.accountNum = accountNum;
        this.bankCode = bankCode;
        this.accountSeq = accountSeq;
        this.accountType = accountType;
        this.holderName = holderName;
        this.users = users;
    }

    public static Account createAccount(String fintechUseNum, String bankName, String accountNum, String bankCode, String accountSeq, AccountType accountType, String holderName, Users users){
        return Account.builder()
                .fintechUseNum(fintechUseNum)
                .bankName(bankName)
                .accountNum(accountNum)
                .bankCode(bankCode)
                .accountSeq(accountSeq)
                .accountType(accountType)
                .holderName(holderName)
                .users(users)
                .build();
    }

    public boolean isMainAccount(){
        if(this.accountType == AccountType.MAIN) return true;
        return false;
    }

    public void updateAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setUsers(Users users) {
        this.users = users;
    }


}
