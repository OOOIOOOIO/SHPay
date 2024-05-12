package com.sh.shpay.global.util.komoran;

import kr.co.shineware.nlp.komoran.model.AnalyzeResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class KomoranSearchStore {

    static class WordAndMorphPair {
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
            WordAndMorphPair compareObj = (WordAndMorphPair) obj;

            return this.word.equals((compareObj.word)) && this.morpheme.equals(compareObj.morpheme);
        }
    }


    private static List<WordAndMorphPair> pronounList = new ArrayList<>();
    private static List<WordAndMorphPair> nounList = new ArrayList<>();
    private static List<WordAndMorphPair> bankList = new ArrayList<>();
    //    private static List<WordAndMorphPair> verbLlist = new ArrayList<>();

    static {

        /**
         * 대명사
         */
        pronounList.add(new WordAndMorphPair("내", "NP"));
        pronounList.add(new WordAndMorphPair("나", "NP"));
        pronounList.add(new WordAndMorphPair("내", "MM"));

        /**
         * 명사(계좌, 잔고 등등)
         */
        nounList.add(new WordAndMorphPair("계좌", "NNG"));
        nounList.add(new WordAndMorphPair("잔액", "NNG"));
        nounList.add(new WordAndMorphPair("통장", "NNG"));

        /**
         * 동사
         * 동사라고 해봤자 그냥 알려줘, 보여줘 등일 것이기에 무시
         */
//        verbLlist.add(new WordAndMorphPair("알리", "VV"));
//        verbLlist.add(new WordAndMorphPair("보이", "VV"));
//        verbLlist.add(new WordAndMorphPair("내놓", "VV"));

        /**
         * 은행"
         */
        bankList.add(new WordAndMorphPair("신한은행", "NNP"));
        bankList.add(new WordAndMorphPair("국민은행", "NNP"));
        bankList.add(new WordAndMorphPair("우리은행", "NNP"));
        bankList.add(new WordAndMorphPair("농협은행", "NNP"));
        bankList.add(new WordAndMorphPair("카카오은행", "NNP"));
        bankList.add(new WordAndMorphPair("하나은행", "NNP"));
        bankList.add(new WordAndMorphPair("기업은행", "NNP"));
        bankList.add(new WordAndMorphPair("산업은행", "NNP"));
        bankList.add(new WordAndMorphPair("수협은행", "NNP"));
        bankList.add(new WordAndMorphPair("전북은행", "NNP"));
        bankList.add(new WordAndMorphPair("제주은행", "NNP"));
        bankList.add(new WordAndMorphPair("대구은행", "NNP"));
    }

    /**
     * 1. 코모란에서 형태소 분석 결과 리스트 받음
     * 2. pronoun || noun = true -> 개인정보
     * 3. false -> chatgpt
     * @param tokenList
     * @return
     */
    public static AnalyzeResultDto analyzeSentence(List<Token> tokenList) {

        List<WordAndMorphPair> wordAndMorphPairList = tokenList.stream().map(wm -> new WordAndMorphPair(wm.getMorph(), wm.getPos()))
                .collect(Collectors.toList());

        AnalyzeResultDto analyzeResultDto = new AnalyzeResultDto(false, false, null);

//        for (WordAndMorphPair pair : collect) {
        for(int i = 0; i < wordAndMorphPairList.size(); i++){


            if(pronounList.contains(wordAndMorphPairList.get(i)) || nounList.contains(wordAndMorphPairList.get(i))){ // 개인정보인지
                analyzeResultDto.setPrivacy(true);

                break;
            }

        }

        for(int i = 0; i < wordAndMorphPairList.size(); i++){

            if (bankList.contains(wordAndMorphPairList.get(i))) { // 특정 은행인지
                analyzeResultDto.setSpecificBank(true);
                if(wordAndMorphPairList.get(i-1).morpheme.equals("SL")) analyzeResultDto.setBankName(wordAndMorphPairList.get(i-1).word + wordAndMorphPairList.get(i).word); // ibk등 영어
                else analyzeResultDto.setBankName(wordAndMorphPairList.get(i).word);

                break;
            }

        }

        return analyzeResultDto;
    }



}
