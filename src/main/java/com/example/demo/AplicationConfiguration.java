package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@ConfigurationProperties(prefix = "data") // menggunakan ini harus menambahana setter dan getter
@EnableAsync
public class AplicationConfiguration {

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String password;

    @Bean
    public EksternalLibrary externalLIbraryBean(){
        return new EksternalLibrary("CUSTOM LIBRARY");
    }

//    public String getUserName(){
//        return this.userName;
//    }
//
//    public String getPassword(){
//        return  this.password;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    @Bean(name = "customExecutor")
    public Executor customExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(0);
        executor.setThreadNamePrefix("CustomAsync-");
        executor.initialize();
        return executor;
    }
}
