//package com.sh.shpay.global.util.redis.pubsub;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class RedisPublisher {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public void publish(ChannelTopic topic, String message) {
//        redisTemplate.convertAndSend(topic.getTopic(), message);
//
//    }
//
//}
