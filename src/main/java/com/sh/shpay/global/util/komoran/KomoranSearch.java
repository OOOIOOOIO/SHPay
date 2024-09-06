package com.sh.shpay.global.util.komoran;

import com.sh.shpay.global.util.komoran.criteria.KomoranBankCriteria;
import com.sh.shpay.global.util.komoran.criteria.KomoranExclustionCriteria;
import com.sh.shpay.global.util.komoran.criteria.KomoranPrivateInfoCriteria;
import com.sh.shpay.global.util.komoran.vo.AnalyzeResultDto;
import com.sh.shpay.global.util.komoran.vo.WordAndMorphPair;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class KomoranSearch {



    /**
     * 1. 코모란에서 형태소 분석 결과 리스트 받음
     * 2. pronoun || noun = true -> 개인정보
     * 3. false -> chatgpt
     */
    public static AnalyzeResultDto analyzeSentence(List<Token> tokenList) {

        List<WordAndMorphPair> wordAndMorphPairList = tokenList.stream().map(wm -> new WordAndMorphPair(wm.getMorph(), wm.getPos()))
                .collect(Collectors.toList());

        AnalyzeResultDto analyzeResultDto = new AnalyzeResultDto(false, false, null);

        //특정 질문 일반질문으로 처리
        for(int i = 0; i < wordAndMorphPairList.size(); i++){
            for(KomoranExclustionCriteria komoranExclustionCriteria : KomoranExclustionCriteria.values()){
                if(wordAndMorphPairList.get(i).equals(komoranExclustionCriteria.getWord() + "," + komoranExclustionCriteria.getMorpheme())){
                    return analyzeResultDto;
                }
            }

        }

        // 개인정보 판별
        boolean privacyFlag = false;
        for(int i = 0; i < wordAndMorphPairList.size(); i++){
            for(KomoranPrivateInfoCriteria komoranPrivateInfoCriteria : KomoranPrivateInfoCriteria.values()){
                if(wordAndMorphPairList.get(i).equals(komoranPrivateInfoCriteria.getWord() + "," + komoranPrivateInfoCriteria.getMorpheme())){
                    analyzeResultDto.setPrivacy(true);

                    privacyFlag = true;
                    break;
                }
            }

            if(privacyFlag) break;
        }

        // 특정은행 판별
        boolean bankFlag = false;
        if(analyzeResultDto.isPrivacy()){
            for(int i = 0; i < wordAndMorphPairList.size(); i++){
                for(KomoranBankCriteria komoranBankCriteria : KomoranBankCriteria.values()){
                    if(wordAndMorphPairList.get(i).equals(komoranBankCriteria.getWord() + "," + komoranBankCriteria.getMorpheme())){
                        analyzeResultDto.setSpecificBank(true);
                        if(i > 0 && wordAndMorphPairList.get(i-1).getMorpheme().equals("SL")){
                            analyzeResultDto.setBankName(wordAndMorphPairList.get(i-1).getWord() + wordAndMorphPairList.get(i).getWord()); //영어일 경우(ibk, ...)
                        }
                        else analyzeResultDto.setBankName(wordAndMorphPairList.get(i).getWord());

                        bankFlag = true;
                        break;

                    }
                }

                if(bankFlag) break;

            }
        }

        return analyzeResultDto;
    }



}
