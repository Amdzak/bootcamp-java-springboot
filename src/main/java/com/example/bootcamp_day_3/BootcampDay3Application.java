package com.example.bootcamp_day_3;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class BootcampDay3Application implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value("${app.kafka.topic}")
	private String topicName;

	private final Integer numberOfMessages = 110;

    public BootcampDay3Application(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

	@Bean
	public NewTopic createTopic() {
		return TopicBuilder.name(topicName)
				.partitions(3)
				.replicas(1)
				.build();
	}

    public static void main(String[] args) {
		SpringApplication.run(BootcampDay3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Memulai pengiriman 100 pesan ke topic: {}", topicName);

		for (int i = 1; i <= numberOfMessages; i++) {
			// Logika Key Modulus (misal modulus 5 agar key berulang 0-4)
			String key = "key-" + (i % 5);
			String message = "Pesan ke-" + i;

			kafkaTemplate.send(topicName, key, message);
		}

		logger.info("Berhasil mengirim " + numberOfMessages + " pesan.");
	}

	// Consumer sederhana untuk memantau pesan masuk
	@KafkaListener(topics = "${app.kafka.topic}", groupId = "group-01")
	public void listen(String message) {
		logger.info("Pesan diterima oleh Consumer: {}", message);
	}
}
