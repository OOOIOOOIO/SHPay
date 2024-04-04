package com.sh.shpay.global.util.openai;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sh.shpay.api.chatbot.api.dto.req.ChatReqDto;
import com.sh.shpay.api.chatbot.api.dto.res.ChatResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenAiApiClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://api.openai.com";

    @Value("${openai.secret}")
    private final String secret;

    /**
     * Create chat completion
     * 질문 보내기
     */
    public void chatCompletion(ChatReqDto chatReqDto){
        String url = BASE_URL + "/v1/chat/completions";

        HttpHeaders httpHeaders = generateHeader("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Authorization", "Bearer " + secret);

        HttpEntity httpEntity = generateHttpEntityWithBody(httpHeaders, chatReqDto);

        ChatResDto chatResDto = restTemplate.exchange(url, HttpMethod.POST, httpEntity, ChatResDto.class).getBody();

        log.info("===============" + chatResDto.getId() + "==============");
        log.info("===============" + chatResDto.getModel() + "==============");
        log.info("===============" + chatResDto.getChoices().get(0).getMessage() + "==============");
        log.info("===============" + chatResDto.getUsage().getTotal_tokens() + "==============");

    }


    /**
     * Header 생성
     */
    private HttpHeaders generateHeader(String name, String val){
        HttpHeaders httpHeaders = new HttpHeaders();
        if(name.equals("Authorization")){
            httpHeaders.add(name, "Bearer " + val);
            return httpHeaders;
        }

        httpHeaders.add(name, val);
        return httpHeaders;
    }

    /**
     * HttpEntity 생성(header만)
     */
    private HttpEntity generateHttpEntity(HttpHeaders httpHeaders){
        return new HttpEntity<>(httpHeaders);
    }

    /**
     * HttpEntity 생성(header + body)
     */
    private HttpEntity generateHttpEntityWithBody(HttpHeaders httpHeaders, ChatReqDto body) {
        return new HttpEntity<>(body, httpHeaders);
    }




}
