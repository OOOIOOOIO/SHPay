package com.sh.shpay.api.chatbot.application;

import com.sh.shpay.api.chatbot.api.dto.req.ChatReqDto;
import com.sh.shpay.api.chatbot.api.dto.req.Message;
import com.sh.shpay.api.chatbot.api.dto.res.AnswerResDto;
import com.sh.shpay.domain.acconut.api.dto.res.AccountListResponseDto;
import com.sh.shpay.domain.acconut.application.AccountService;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.OpenbankingTokenInfoFromHeaderDto;
import com.sh.shpay.global.util.komoran.AnalyzeResultDto;
import com.sh.shpay.global.util.komoran.KomoranUtil;
import com.sh.shpay.global.util.openai.OpenAiApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotService {

    @Value("${openai.model}")
    private String model;
    @Value("${openai.user}")
    private String user;

    private final OpenAiApiClient openAiApiClient;
    private final AccountService accountService;
    private final KomoranUtil komoranUtil;



    /**
     * Create chat completion
     * return 값 어떻게 하지
     * 1. 계좌리스트
     * 2. chatgpt
     * 질문 보내기
     */
    @LogTrace
    public AnswerResDto requestChatCompletion(String question,
                                        OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto,
                                        UserInfoFromSessionDto userInfoFromSessionDto){
        String sentence = question.trim(); // komoran: 맨 뒤 공백이 있을 경우 토큰으로 못 자름

        AnalyzeResultDto analyzeResultDto = komoranUtil.analyzeSentence(sentence); // 분석완료

        if(analyzeResultDto.isPrivacy()){ // 내 계좌 정보

            if(analyzeResultDto.isSpecificBank()){ // 특정은행만

                // 특정은행 리스트 리턴
                AccountListResponseDto accountListResponseDto = accountService.requestSpecificAccountList(openbankingTokenInfoFromHeaderDto, userInfoFromSessionDto, analyzeResultDto.getBankName());

                return new AnswerResDto(accountListResponseDto, sentence, null);
            }

            // 은행리스트 리턴
            AccountListResponseDto accountListResponseDto = accountService.requestAccountList(openbankingTokenInfoFromHeaderDto, userInfoFromSessionDto);

            return new AnswerResDto(accountListResponseDto, sentence, null);


        }

        String completionToChatGpt = chatCompletionToChatGpt(analyzeResultDto.getSentence()); // chatgpt 질문결과

        return new AnswerResDto(null, sentence, completionToChatGpt); // 일반금융정보


    }


    /**
     * chatgpt에게 질문하기
     *
     */
    private String chatCompletionToChatGpt(String question){
        Message message = Message.builder()
                .content(question)
                .role(user)
                .build();

        List<Message> messages = Collections.singletonList(message);

        ChatReqDto chatReqDto = ChatReqDto.builder()
                .model(model)
                .messages(messages)
                .build();

//        return openAiApiClient.chatCompletion(chatReqDto);
        return "a";
    }


    /**
     * 계좌내역 조회
     *
     */
    private void requestAccount(){


    }


}
/**
 * 기능
 * - 잔액조회
 * - 거래내역 조회
 * - 금융지식 물어보기
 * 만약 특정 은행이 있을 경우 -> 잔고 및 거래 내역을 보여주기
 *
 * 특정 은행이 없을 경우 -> 은행을 정확히 입력해주세요
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
 * 신한은행 챗봇 참고
 *
 * 보유계좌리스트 노출
 * 계좌정보 : 계좌(NNG) + 정보(NNG)
 * 계좌내역 : 계좌(NNG) + 내역(NNG)
 * 잔액정보 : 잔액(NNG) + 정보(NNG)
 * 잔액내역 : 잔액(NNG) + 내역(NNG)
 * 통장잔고 : 통장(NNG) + 잔고(NNG)
 * 통장내역 : 통장(NNG) + 내역(NNG)
 * 신한은행 계좌정보 : 신한은행(NNP) -> 특정은행 계좌정보 노출
 * ibk기업은행, kb산업은행 -> 앞에 영어 있는 은행들 "은행을 포함하고 있으면" 앞에 있는 단어까지 합쳐서 return하기
 *
 * 신한, 국민 ... 신한 은행 -> 신한(NNG) + 은행(NNG)
 *
 * 트리로 만들어놔야겠네, 이거 자료구조 찾아봐야겠다
 *
 *
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
