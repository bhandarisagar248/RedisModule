package com.redisDemo.Controller;

import com.redisDemo.Service.RedisPublisher;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private RedisPublisher publisher;

    @Autowired
    private StringRedisTemplate template;

    @GetMapping("/redis-test")
    public String test() {
        template.opsForValue().set("test", "ok");
        return template.opsForValue().get("test");
    }


    @GetMapping("/login")
    public String login(HttpSession session){
        session.setAttribute("user","logged-in");
        return "Session ID: " + session.getId();
    }

    @PostMapping("/send")
    public String send(@RequestParam String msg) {
        publisher.publish(msg);
        return "Sent"+msg;
    }
}
