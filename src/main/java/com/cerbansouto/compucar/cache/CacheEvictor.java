package com.cerbansouto.compucar.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictor {

    @CacheEvict(value = "mechanics", allEntries = true)
    public void evictMechanicsCache() { }

    @CacheEvict(value = "readers", allEntries = true)
    public void evictReadersCache() { }
}
