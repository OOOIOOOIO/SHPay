package com.sh.shpay.api.chatbot.application;

import com.sh.shpay.api.chatbot.api.dto.req.ChatReqDto;
import com.sh.shpay.api.chatbot.api.dto.req.Message;
import com.sh.shpay.api.chatbot.api.dto.res.AnswerResDto;
import com.sh.shpay.domain.acconut.api.dto.res.AccountListResponseDto;
import com.sh.shpay.domain.acconut.application.AccountService;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeaderDto;
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
     * Create chat completion & return 사용자개인정보
     */
    @LogTrace
    public AnswerResDto requestChatCompletion(String question,
                                        Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto,
                                        UserInfoFromSessionDto userInfoFromSessionDto){
        String sentence = question.trim(); // komoran: 맨 뒤 공백이 있을 경우 토큰으로 못 자름

        AnalyzeResultDto analyzeResultDto = komoranUtil.analyzeSentence(sentence); // 분석완료

        if(analyzeResultDto.isPrivacy()){ // 내 계좌 정보

            if(analyzeResultDto.isSpecificBank()){ // 특정은행만

                // 특정은행 리스트 리턴
                AccountListResponseDto accountListResponseDto = accountService.requestSpecificAccountList(openbanking3LeggedTokenFromHeaderDto, userInfoFromSessionDto, analyzeResultDto.getBankName());

                return new AnswerResDto(accountListResponseDto, sentence, null);
            }

            // 은행리스트 리턴
            AccountListResponseDto accountListResponseDto = accountService.requestAccountList(openbanking3LeggedTokenFromHeaderDto, userInfoFromSessionDto);

            return new AnswerResDto(accountListResponseDto, sentence, null);


        }

        String completionToChatGpt = chatCompletionToChatGpt(analyzeResultDto.getSentence()); // chatgpt 질문결과

        return new AnswerResDto(null, sentence, completionToChatGpt); // 일반금융정보


    }


    /**
     * chatgpt에게 질문하기
     *
     */
    @LogTrace
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

        return openAiApiClient.chatCompletion(chatReqDto);
    }




}

