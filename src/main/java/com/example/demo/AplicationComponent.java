package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class AplicationComponent {
    public String getTimeNow(){
        return "[COMPONENT] Current time now" + java.time.LocalDateTime.now();
    }

}
