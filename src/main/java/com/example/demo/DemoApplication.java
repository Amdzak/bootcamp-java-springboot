package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	private final static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	private final AplicationComponent aplicationComponent;
	private final AplicationService aplicationService;
	private final AplicationConfiguration aplicationConfiguration;
	private final EksternalLibrary eksternalLibrary;
	private final AsyncService asyncService;

	@Autowired
	private UserService userService;

	public DemoApplication(
			AplicationComponent aplicationComponent,
			AplicationService aplicationService,
			EksternalLibrary eksternalLibrary,
			AplicationConfiguration aplicationConfiguration,
			AsyncService asyncService
	){
		this.aplicationComponent = aplicationComponent;
		this.aplicationService = aplicationService;
		this.eksternalLibrary = eksternalLibrary;
		this.aplicationConfiguration = aplicationConfiguration;
		this.asyncService = asyncService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("\n-------------MAIN-------------");
		logger.info("[TIME] {}", this.aplicationComponent.getTimeNow());
		logger.debug("[MESSAGNE] {}", this.aplicationService.sendNotificaition("HALOWWWWWW"));
		logger.debug("[EKSTERNAL LIB] {}", this.aplicationConfiguration.externalLIbraryBean().getLibraryName());
		logger.warn("[GET USERNAME FORM .properties] {}", this.aplicationConfiguration.getUserName());
		logger.error("[GET PASSWORD FORM .properties] {}", this.aplicationConfiguration.getPassword());
		logger.info("\n-------------END OF MAIN-------------");

//		logger.debug("------------------ASYNC SERVICE------------------");
//		this.asyncService.doMyLongTask("Fandi");
//		this.asyncService.doMyLongTask("Helmy");
//		this.asyncService.doMyLongTask("Kurnaiwan");
//		logger.debug("------------------END OF ASYNC SERVICE------------------");

//		logger.debug("--------COMPLITABLE FUTURE ASYNC--------");
//		Integer userId1 = 4;
//		Integer userId2 = 2;
//
//		logger.info("Fiding user");
//		CompletableFuture<String> result1 = this.asyncService.findUserAsync(userId1);
//		CompletableFuture<String> result2 = this.asyncService.findUserAsync(userId2);
//
//		logger.info("Waiting ressult");
//		CompletableFuture.allOf(result1, result2).join();
//
//		logger.info("Result for userId1 : " + userId1 + " is " + result1.get());
//		logger.info("Result for userId2 : " + userId2 + " is " + result2.get());
//		logger.debug("--------END OF COMPLITABLE FUTURE ASYNC--------");

	}
}
