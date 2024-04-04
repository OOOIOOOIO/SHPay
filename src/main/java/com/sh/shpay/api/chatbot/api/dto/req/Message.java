package com.sh.shpay.api.chatbot.api.dto.req;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    private String role;
    private String content;

    @Builder
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
