package com.onboarding.core.global.utils;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisLock {
  private final RedisTemplate<String, String> redisTemplate;

  public boolean lock(String key, long timeoutInSeconds) {
    Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "LOCKED", timeoutInSeconds, TimeUnit.SECONDS);
    return success != null && success;
  }

  public void unlock(String key) {
    redisTemplate.delete(key);
  }
}
