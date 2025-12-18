package com.redisDemo.Configuration;

import com.redisDemo.Component.RedisSubscriber;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@Configuration
public class RedisListenerConfig {


    @Bean
    MessageListenerAdapter messageListener(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory, RedisSubscriber subscriber){
//        📌 This container:
//        Opens a Redis subscription
//        Listens to socket-channel
//        Calls onMessage() on every instance

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(subscriber,
                new PatternTopic("socket-channel"));
        return container;
    }
}
