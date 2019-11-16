package com.cerbansouto.compucar.context;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:application-context-messaging.xml")
public class MessagingConfig {
    @Value("${queue.connection.timeout}")
    private long timeout;

    @Value("${queue.broker.url}")
    private String brokerUrl;

    @Bean
    public PooledConnectionFactory connectionFactory() {
        PooledConnectionFactory connectionFactory = new PooledConnectionFactory();
        connectionFactory.setConnectionFactory(directConnectionFactory());
        connectionFactory.setExpiryTimeout(timeout);
        return connectionFactory;
    }

    @Bean
    public ActiveMQConnectionFactory directConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        return connectionFactory;
    }
}
