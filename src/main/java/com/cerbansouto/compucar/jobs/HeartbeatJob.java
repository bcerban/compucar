package com.cerbansouto.compucar.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class HeartbeatJob {
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private int applicationPort;

    @Value("${monitor.url}")
    private String monitorUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedRateString = "${monitor.heartbeat.rate}")
    public void sendHeartbeat() {
        try {
            Heartbeat heartbeat = new Heartbeat(applicationName, applicationPort);
            String url = String.format("%s/heartbeats", monitorUrl);
            ResponseEntity<Object> response = restTemplate.postForEntity(url, new HttpEntity<>(heartbeat), Object.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                log.info("Sent heartbeat to monitor");
            } else {
                log.error("Heartbeat notification failed :(");
            }
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }
}
