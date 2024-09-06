package com.sh.shpay.global.util.komoran.criteria;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 은행목록
 */
@Getter
@AllArgsConstructor
public enum KomoranBankCriteria {
    SHINHAN("신한은행", "NNP"),
    KB("국민은행", "NNP"),
    WR("우리은행", "NNP"),
    NH("농협은행", "NNP"),
    KKOB("카카오뱅크", "NNP"),
    TSB("토스뱅크", "NNP"),
    HN("하나은행", "NNP"),
    IBK("기업은행", "NNP"),
    KDB("산업은행", "NNP"),
    SH("수협은행", "NNP"),
    JB("전북은행", "NNP"),
    JJ("제주은행", "NNP"),
    DG("대구은행", "NNP");

    private final String word;
    private final String morpheme;

}


