package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class AplicationService {

    public String sendNotificaition(String message){
        return "[SERVICE] Notification "+ message;
    }
}
