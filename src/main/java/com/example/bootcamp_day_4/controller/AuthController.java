package com.example.bootcamp_day_4.controller;

import com.example.bootcamp_day_4.dto.LoginRequest;
import com.example.bootcamp_day_4.dto.WebResponse;
import com.example.bootcamp_day_4.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public WebResponse<String> login(@RequestBody LoginRequest request) {
        // HARDCODED VALIDATION
        if ("admin".equals(request.getUsername()) && "admin123".equals(request.getPassword())) {

            String token = jwtUtil.generateToken(request.getUsername());

            return WebResponse.<String>builder()
                    .message("Login Success")
                    .data(token)
                    .build();
        } else {
            return WebResponse.<String>builder()
                    .message("Login failed")
                    .errors("Wrong username or password!")
                    .build();
        }
    }
}