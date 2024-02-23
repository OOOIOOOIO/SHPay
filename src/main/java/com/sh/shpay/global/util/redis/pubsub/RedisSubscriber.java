//package com.sh.shpay.global.util.redis.pubsub;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class RedisSubscriber implements MessageListener {
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        // simple message 테스트
//        // subscriber가 message를 받는다, 여기서 api를 불러야 된다~~~~~ 하아... pub은 그냥 서버에 보내는 트리거 용도
//        try {
//            String msg = (String) redisTemplate.getStringSerializer()
//                    .deserialize(message.getBody());
//
//            String data = objectMapper.readValue(msg, String.class);
//            log.info("pub을 통해 받은 sub의 message : " + data);
//        } catch (Exception e){
//            log.error(e.getMessage());
//        }
//
//        log.info("Message received : " + new String(message.getBody()));
//    }
//
//
//
//}
