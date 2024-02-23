//package com.sh.shpay.global.util.redis.pubsub;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PubSubService {
//    private final RedisMessageListenerContainer container;
//    private final RedisSubscriber redisSubscriber;
//    private final RedisPublisher redisPublisher;
//    private final ChannelTopic topic;
//
//
//    public void publish(ChannelTopic topic, String message){
//        redisPublisher.publish(topic, message);
//    }
//    public void subscribeChannel(String event){
//        container.addMessageListener(redisSubscriber, new ChannelTopic(event));
//
//    }
//
//    public void unsubscribeChannel(String event){
//        container.removeMessageListener(redisSubscriber, new ChannelTopic(event));
//    }
//
//    public void genNewTopic(ChannelTopic topic){
//        container.addMessageListener(redisSubscriber, topic);
//    }
//}
