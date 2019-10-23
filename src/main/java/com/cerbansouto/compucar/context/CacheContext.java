package com.cerbansouto.compucar.context;

import com.cerbansouto.compucar.cache.TimeoutCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheContext {
    private long cacheTimeout = 240;
    private String timeUnit = "MINUTES";
    private long cacheMaximunSize = 100;

    @Bean
    public CacheManager cacheManager() {
        TimeoutCacheManager cacheManager = new TimeoutCacheManager();
        cacheManager.setCacheTimeout(cacheTimeout);
        cacheManager.setTimeUnit(TimeUnit.valueOf(timeUnit));
        cacheManager.setMaximumSize(cacheMaximunSize);

        return cacheManager;
    }
}
