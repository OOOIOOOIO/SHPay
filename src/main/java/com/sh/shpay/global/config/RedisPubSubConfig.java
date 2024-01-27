package com.sh.shpay.global.config;

import com.sh.shpay.global.util.redis.pubsub.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisPubSubConfig {

    private final RedisSubscriber redisSubscriber;

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(redisSubscriber);
    }

    //redis pub/sub
    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(redisSubscriber, myTopic());

        return container;
    }

    @Bean
    public ChannelTopic myTopic(){
        return new ChannelTopic("coupon");
    }
}
