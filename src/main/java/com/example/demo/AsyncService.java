package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {
    private final static Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private UserService userService;

    public CompletableFuture<String> findUserAsync(Integer id){
        String foundUserName = this.userService.findUser(id);
        return CompletableFuture.completedFuture(foundUserName);
    }

    @Async
    public void doMyLongTask(String userName) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            logger.info("Running my long task " + i + "for user " + userName);
            Thread.sleep(500);
        }
    }

    @Async
    public void doMyLongTaskB() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            logger.info("Running my long task B " + i);
            Thread.sleep(3000);
        }
    }

    @Async
    public void doMyLongTaskC() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            logger.info("Running my long task C " + i);
            Thread.sleep(3000);
        }
    }
}
