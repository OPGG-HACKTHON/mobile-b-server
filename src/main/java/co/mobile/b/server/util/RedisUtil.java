package co.mobile.b.server.util;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

@AllArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;



}
