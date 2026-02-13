package com.example.bootcamp_day_4.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfiguration {

    @Value("${app.kafka.topic}")
    private String topicName;

    // Create kafka topic with 3 partition
    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(topicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
