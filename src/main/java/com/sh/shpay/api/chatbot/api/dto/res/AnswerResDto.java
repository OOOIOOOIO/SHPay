package com.sh.shpay.api.chatbot.api.dto.res;

import com.sh.shpay.domain.acconut.api.dto.res.AccountListResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerResDto {
    private AccountListResponseDto accountListResponseDto;
    private String question;
    private String chatCompletion;

    public AnswerResDto(AccountListResponseDto accountListResponseDto, String question, String chatCompletion) {
        this.accountListResponseDto = accountListResponseDto;
        this.question = question;
        this.chatCompletion = chatCompletion;
    }
}
