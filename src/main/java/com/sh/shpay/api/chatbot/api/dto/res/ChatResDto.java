package com.sh.shpay.api.chatbot.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatResDto {
    private String id;
    private String object;
    private String created;
    private String model;
    private List<Choice> choices;
    private Usage usage;

}
