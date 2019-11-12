package com.cerbansouto.compucar.context;

import com.cerbansouto.compucar.cache.TimeoutCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheContext {

    @Value("${cache.timeout}")
    private long cacheTimeout; // = 240;

    @Value("${cache.timeout.unit}")
    private String timeUnit; // = "MINUTES";

    @Value("${cache.max.size}")
    private long cacheMaximunSize; // = 100;

    @Bean
    public CacheManager cacheManager() {
        TimeoutCacheManager cacheManager = new TimeoutCacheManager();
        cacheManager.setCacheTimeout(cacheTimeout);
        cacheManager.setTimeUnit(TimeUnit.valueOf(timeUnit));
        cacheManager.setMaximumSize(cacheMaximunSize);

        return cacheManager;
    }
}
