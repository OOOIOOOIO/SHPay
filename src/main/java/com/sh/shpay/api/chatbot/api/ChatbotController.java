package com.sh.shpay.api.chatbot.api;


import com.sh.shpay.api.chatbot.application.ChatbotService;
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
    @PostMapping("")
    public ResponseEntity<String> requestChatCompletion(@RequestParam(value = "question") String question){

        String answer = chatbotService.chatCompletion(question);

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }
}
