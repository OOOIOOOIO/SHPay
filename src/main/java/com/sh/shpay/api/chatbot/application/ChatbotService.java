package com.sh.shpay.api.chatbot.application;

import com.sh.shpay.api.chatbot.api.dto.req.ChatReqDto;
import com.sh.shpay.api.chatbot.api.dto.req.Message;
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


    /**
     * Create chat completion
     * 질문 보내기
     */
    public String chatCompletion(String question){

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
