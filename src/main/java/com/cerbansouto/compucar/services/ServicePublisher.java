package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.model.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServicePublisher {
    @Autowired
    private MessageChannel serviceChannel;

    public void publish(Service service) {
        try {
            Message<Service> message = MessageBuilder.withPayload(service).build();
            serviceChannel.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
