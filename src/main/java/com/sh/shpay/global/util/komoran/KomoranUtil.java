package com.sh.shpay.global.util.komoran;

import com.sh.shpay.global.log.LogTrace;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class KomoranUtil {

    private final Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);


    @Getter
    private class Morpheme{
        private String morph;
        private String poS;

        public Morpheme(String morph, String poS) {
            this.morph = morph;
            this.poS = poS;
        }

    }

    @LogTrace
    public AnalyzeResultDto analyzeSentence(String sentence){

        // 형태소 분석
        KomoranResult analyze = komoran.analyze(sentence);

        // 개인정보인지 금융정보인지 판단
        List<Token> tokenList = analyze.getTokenList();

        AnalyzeResultDto analyzeResultDto = KomoranSearchStore.analyzeSentence(tokenList);

        if(analyzeResultDto.isPrivacy()){ // 개인정보
            return analyzeResultDto;
        }

        analyzeResultDto.setSentence(sentence); //gpt

        return analyzeResultDto;
    }



}
