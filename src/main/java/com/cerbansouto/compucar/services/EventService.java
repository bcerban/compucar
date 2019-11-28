package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.model.ServiceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            String createUrl = String.format("%s%s", url, eventsEndpoint);
            HttpEntity<ServiceEvent> response = restTemplate.postForEntity(
                    createUrl,
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

    public List<ServiceEvent> fetchEvents(String serviceCode) {
        List<ServiceEvent> events = new ArrayList<>();
        List<String> names = fetchEventNames(serviceCode);
        names.forEach(name -> {
            try {
                HttpEntity<ServiceEvent> response = restTemplate.getForEntity(
                        String.format("%s%s/%s/name/%s", url, eventsEndpoint, serviceCode, name),
                        ServiceEvent.class
                );

                if (response.getBody() != null) {
                    events.add(response.getBody());
                }
            } catch (RestClientException e) {
                log.error(e.getMessage(), e);
            }
        });

        return events;
    }

    public List<String> fetchEventNames(String serviceCode) {
        List<String> names = new ArrayList<>();

        try {
            HttpEntity<String[]> response = restTemplate.getForEntity(
                    String.format("%s%s/%s", url, eventsEndpoint, serviceCode),
                    String[].class
            );

            if (response.getBody() != null) {
                log.info("Fetched event names for {}", serviceCode);
                names = Arrays.asList(response.getBody());
            }
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }

        return names;
    }
}
