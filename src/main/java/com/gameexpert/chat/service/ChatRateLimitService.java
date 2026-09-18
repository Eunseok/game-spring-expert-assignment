package com.gameexpert.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRateLimitService {

    private final RedisScript<Long> rateLimitScript =
            RedisScript.of(new ClassPathResource("scripts/rate_limit.lua"), Long.class);
    private final StringRedisTemplate redisTemplate;

    public boolean allow(Long playerId) {
        String key = "chat:limit:" + playerId;
        Long result = redisTemplate.execute(
                rateLimitScript,
                List.of(key),
                "5",   // ARGV[1]: 최대 허용 횟수
                "10"   // ARGV[2]: 만료 시간(초)
        );
        return result != null && result == 1L;
    }
}
