package com.cerbansouto.compucar.cron;

import com.cerbansouto.compucar.cache.CacheEvictor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class CacheCleaner {

    @Autowired
    private CacheEvictor cacheEvictor;

    @Scheduled(cron = "* * */4 * * ?")
    public void action() {
        try {
            log.info("###### Beginning cache clearing process ######");

            cacheEvictor.evictMechanicsCache();
            cacheEvictor.evictReadersCache();
        } catch (Exception e) {
            log.error("Error while clearing cache.", e);
        }

        log.info("###### Ended cache clearing process ######");
    }
}
