package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Register API
    @PostMapping("/register")
    public String register(
            @RequestBody User user) {

        return authService.register(user);
    }

    // Login API
    @PostMapping("/login")
    public Map<String, String> login(

            @RequestParam String username,

            @RequestParam String password) {

        String token =
                authService.login(
                        username,
                        password
                );

        return Map.of(
                "token",
                token
        );
    }
}