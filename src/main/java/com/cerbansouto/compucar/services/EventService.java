package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.model.ServiceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class EventService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${event.repository.url}")
    private String url;

    @Value("${event.repository.create.endpoint}")
    private String eventsEndpoint;

    public void create(ServiceEvent event) {
        try {
            HttpEntity<ServiceEvent> response = restTemplate.postForEntity(
                    String.format("%s%s", url, eventsEndpoint),
                    new HttpEntity<>(event),
                    ServiceEvent.class
            );

            if (response.getBody() != null) {
                log.info("Event {} for service {} created successfully", event.getName(), event.getServiceCode());
            } else {
                log.info("Event {} for service {} not created", event.getName(), event.getServiceCode());
            }
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }
}
