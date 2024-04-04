package com.sh.shpay.api.chatbot.api.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Usage {
    private int completion_tokens;
    private int prompt_tokens;
    private int total_tokens;

    @Builder
    public Usage(int completion_tokens, int prompt_tokens, int total_tokens) {
        this.completion_tokens = completion_tokens;
        this.prompt_tokens = prompt_tokens;
        this.total_tokens = total_tokens;
    }
}
