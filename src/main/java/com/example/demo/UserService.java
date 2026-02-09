package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<Integer, String> users = new HashMap<>();

    @PostConstruct
    private void onCunstructed(){
        users.put(1, "Fandy");
        users.put(2, "Rizky");
        users.put(3, "Hmid");
        users.put(4, "Rudi");
        users.put(5, "Gopal");
        users.put(6, "Anton");
    }

    public String findUser(Integer userId){
        return this.users.get(userId);
    }
}
