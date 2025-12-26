package com.redisDemo.Service;


import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

@Service
public class RideEventStreamConsumer {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    private static final String STREAM = "ride-events";
    private static final String GROUP = "ride-group";
    private static final String CONSUMER = UUID.randomUUID().toString();

    public RideEventStreamConsumer(
            RedisTemplate<String, Object> redisTemplate,
            SimpMessagingTemplate messagingTemplate) {
        this.redisTemplate = redisTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void init() {
        try {
            redisTemplate.opsForStream()
                    .createGroup(STREAM, GROUP);
        } catch (Exception ignored) {
            // Group already exists
            throw new RuntimeException("Group Already Exists");
        }

        startReading();
    }

    private void startReading() {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                List<MapRecord<String, Object, Object>> records =
                        redisTemplate.opsForStream().read(
                                Consumer.from(GROUP, CONSUMER),
                                StreamReadOptions.empty().block(Duration.ofSeconds(2)),
                                StreamOffset.create(STREAM, ReadOffset.lastConsumed())
                        );

                if (records != null) {
                    for (MapRecord<String, Object, Object> record : records) {
                        String message = record.getValue().get("data").toString();
                        //check the message if not the ride-events will be

                        //  Push to WebSocket
                        messagingTemplate.convertAndSend(
                                "/topic/messages", message);

                        // ACK
                        redisTemplate.opsForStream()
                                .acknowledge(STREAM, GROUP, record.getId());
                    }
                }
            }
        });

    }
}

