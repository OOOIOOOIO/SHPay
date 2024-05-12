package com.sh.shpay.api.chatbot.api;


import com.sh.shpay.api.chatbot.api.dto.res.AnswerResDto;
import com.sh.shpay.api.chatbot.application.ChatbotService;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.OpenbankingTokenInfoFromHeader;
import com.sh.shpay.global.resolver.token.OpenbankingTokenInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final ChatbotService chatbotService;

    /**
     * 토큰이 있을 때만 가능하게 할건지
     * @param question
     */
    @LogTrace
    @PostMapping("")
    public ResponseEntity<AnswerResDto> requestChatCompletion(@RequestParam(value = "question") String question,
                                                        @OpenbankingTokenInfoFromHeader OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto,
                                                        @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){

        AnswerResDto answerResDto = chatbotService.requestChatCompletion(question, openbankingTokenInfoFromHeaderDto, userInfoFromSessionDto);

        return new ResponseEntity<>(answerResDto, HttpStatus.OK);
    }

}