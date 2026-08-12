package com.ayub.assessment.api.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.context.annotation.Bean;
import java.time.Duration;

@Configuration @EnableCaching
public class CacheConfig {
    @Bean RedisCacheManager cacheManager(org.springframework.data.redis.connection.RedisConnectionFactory factory){
        RedisCacheConfiguration config=RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10));
        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }
}
