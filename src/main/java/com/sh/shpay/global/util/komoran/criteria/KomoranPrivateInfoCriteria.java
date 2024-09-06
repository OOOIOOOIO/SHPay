package com.sh.shpay.global.util.komoran.criteria;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 개인정보(대명사)
 * 개인정보(명사)
 * 개인정보로 분기
 */
@Getter
@AllArgsConstructor
public enum KomoranPrivateInfoCriteria {
    PR1("내", "NP"),
    PR2("나", "NP"),
    PR3("내", "MM"),
    N1("계좌", "NNG"),
    N2("잔액", "NNG"),
    N3("통장", "NNG");

    private final String word;
    private final String morpheme;
}


