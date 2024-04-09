package com.sh.shpay.global.util.komoran;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class KomoranUtil {

    private final Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

    public void analyzeSentence(String sentence){

        KomoranResult analyze = komoran.analyze(sentence);

        List<Token> tokenList = analyze.getTokenList();

        for (Token token : tokenList) {
            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
        }


    }


}
