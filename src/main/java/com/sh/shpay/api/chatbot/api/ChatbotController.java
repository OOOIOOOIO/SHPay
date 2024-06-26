package com.sh.shpay.api.chatbot.api;


import com.sh.shpay.api.chatbot.api.dto.res.AnswerResDto;
import com.sh.shpay.api.chatbot.application.ChatbotService;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeader;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chatbot", description = "Chatbot API")
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
    @Operation(
            summary = "Chatbot 질문 API",
            description = "Chatbot"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Chatbot 질문에 성공하였습니다."
    )
    @LogTrace
    @PostMapping("")
    public ResponseEntity<AnswerResDto> requestChatCompletion(@RequestParam(value = "question") String question,
                                                        @Openbanking3LeggedTokenFromHeader Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto,
                                                        @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){

        AnswerResDto answerResDto = chatbotService.requestChatCompletion(question, openbanking3LeggedTokenFromHeaderDto, userInfoFromSessionDto);

        return new ResponseEntity<>(answerResDto, HttpStatus.OK);
    }

}
