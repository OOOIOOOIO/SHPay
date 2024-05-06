package com.sh.shpay.global.util.komoran;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class KomoranUtil {

    private final Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

    /**
     * 기능
     * - 잔액조회
     * - 거래내역 조회
     * - 금융지식 물어보기
     * 만약 특정 은행이 있을 경우 -> 잔고 및 거래 내역을 보여주기
     *
     * 특정 은행이 없을 경우 -> 은행을 정확히 입력해주세요
     *
     *
     *
     * ex)
     * 내 계좌들을 보여줘
     * 계좌들을 보여줘
     * 내 신한은행 계좌잔액 좀 알려
     *
     *
     *
     *
     *
     * ---
     * 나의 정보에 대한 것 판별 기준
     * 나(NP)
     * 내(NP)
     * 나(NP) + 의(JKG)
     * 내(MM) + 것(NNB)
     * 나(NP) + 의(JKG) + 껏(NNB)
     * 내(NP) + 껏(XSN)
     *
     * 내(NP) + 끄(VV) + 어(EC)
     *
     *
     * +++++++++++++
     *
     * 계좌정보 : 계좌(NNG) + 정보(NNG)
     * 계좌내역 : 계좌(NNG) + 내역(NNG)
     * 잔액내역 : 잔액(NNG) + 내역(NNG)
     * 통장잔고 : 통장(NNG) + 잔고(NNG)
     *
     * +++++++++++++
     *
     * 알려줘 : 알리(VV) + 어(EC) + 주(VX) + 어(EC)
     * 보여줘 : 보이(VV) + 어(EC) + 주(VX) + 어(EC)
     * 내놔 : 내놓(VV) + 아(EC)
     *
     *
     * =====> 계좌, 통장 ... 이 있을 경우 "본인"에 대한 정보라고 간주(로그인 후 사용가능)
     *      ===> 특정 은행 입력 -> 바로 조회
     *      ===> 단순 요청 -> "은행 이름을 포함해 다시 요청해주세요" 문구 노출
     *
     * =====> 그 외는 ChatGPT에게 맡기기
     *
     *
     */
    @Getter
    private class Morpheme{
        private String morph;
        private String poS;

        public Morpheme(String morph, String poS) {
            this.morph = morph;
            this.poS = poS;
        }

    }

//    private List<Morpheme> morphemeList = new ArrayList<>();
//
//    {
//        morphemeList.add(new Morpheme(""));
//    }

    /**
     * 개인정보인지 아니면 그냥 금융지식인지에 판단
     *
     * return true : 개인정보
     * ㅎ
     * 근데 특정 은행의 계좌인지 아니면 모든 계좌리스트를 보여달라는건지
     *
     * feat. 신한은행의 뭐 어떤 ~~이 있는지 알려줘 같은 기능도 있으면 좋겠네. 오늘 환율 알려줘?
     */
    public boolean analyzeSentence(String sentence){

        KomoranResult analyze = komoran.analyze(sentence);

        List<Token> tokenList = analyze.getTokenList();

        for (Token token : tokenList) {
            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
        }


        return false;
    }



}
