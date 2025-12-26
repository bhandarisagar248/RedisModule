package com.redisDemo.Service;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisPublisher {

    private final RedisTemplate<String,Object> redisTemplate;

    public RedisPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    //Method to Publish message On Redist Stream or Websocket
    public void publish(String eventJson) {

        Map<String, String> payload = new HashMap<>();
        payload.put("data", eventJson);
//        redisTemplate.convertAndSend("socket-channel", eventJson);
//        redisTemplate.opsForStream().add("ride-events", Map.of("data", message));
        redisTemplate.opsForStream().add("ride-events", payload);

    }

}
