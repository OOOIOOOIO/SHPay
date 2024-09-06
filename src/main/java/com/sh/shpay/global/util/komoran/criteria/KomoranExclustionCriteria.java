package com.sh.shpay.global.util.komoran.criteria;


import lombok.AllArgsConstructor;
import lombok.Getter;



/**
 * 일반질문 분기 기준
 */
@Getter
@AllArgsConstructor
public enum KomoranExclustionCriteria {

    V1("개설", "NNG"),
    V2("만들", "VV"),
    V3("생성", "VV"),
    V4("삭제", "NNG"),
    V5("철회", "NNG");
    private final String word;
    private final String morpheme;
}


