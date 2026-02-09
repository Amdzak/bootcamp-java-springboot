package com.dailytask.taskday1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogGeneratorThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(LogGeneratorThread.class);

    private Integer threadNumber;

    public LogGeneratorThread(Integer threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {

            logger.info("Thread {} -> processing data number {}", threadNumber, i);

            // Logger warning
            if (i % 2000 == 0) {
                logger.warn("Thread {} hitting warning threshold at {}", threadNumber, i);
            }

            // Logger error
            if (i % 5000 == 0) {
                logger.error("Thread {} simulated error at {}", threadNumber, i);
            }
        }
    }
}
