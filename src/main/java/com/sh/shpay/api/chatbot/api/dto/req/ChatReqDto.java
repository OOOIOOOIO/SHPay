package com.sh.shpay.api.chatbot.api.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatReqDto {
    private List<Message> messages;
    private String model;

    @Builder
    public ChatReqDto(List<Message> messages, String model) {
        this.messages = messages;
        this.model = model;
    }

}
