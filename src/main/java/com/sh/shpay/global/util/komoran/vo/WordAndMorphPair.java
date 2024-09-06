package com.sh.shpay.global.util.komoran.vo;

import lombok.Getter;

@Getter
public class WordAndMorphPair {
    private String word;
    private String morpheme;

    public WordAndMorphPair(String word, String morpheme) {
        this.word = word;
        this.morpheme = morpheme;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        return (this.word + "," + this.morpheme).equals(String.valueOf(obj));
    }
}
