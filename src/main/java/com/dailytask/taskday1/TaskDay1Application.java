package com.dailytask.taskday1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class TaskDay1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TaskDay1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(5);

		for (int i = 1; i <= 5; i++) {
			executor.submit(new LogGeneratorThread(i));
		}

		executor.shutdown();
	}
}
