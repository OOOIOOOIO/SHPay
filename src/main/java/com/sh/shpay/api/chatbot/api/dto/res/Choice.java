package com.sh.shpay.api.chatbot.api.dto.res;

import com.sh.shpay.api.chatbot.api.dto.req.Message;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Choice {
    private int index;
    private Message message;
    private String finish_reason;

    @Builder
    public Choice(int index, Message message, String finish_reason) {
        this.index = index;
        this.message = message;
        this.finish_reason = finish_reason;
    }
}
