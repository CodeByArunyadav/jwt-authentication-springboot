package com.example.demo.controller;

import com.example.demo.DTO.AuthResponseDTO;
import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	// Register API
	@PostMapping("/register")
	public String register(@RequestBody User user) {

		return authService.register(user);
	}

	// Login API
	@PostMapping("/login")
	public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {

		return authService.login(request.getUsername(), request.getPassword());
	}
	
	// Refresh Token API
		@PostMapping("/refresh")
		public AuthResponseDTO refresh(@RequestBody Map<String, String> request) {			
			String refreshToken = request.get("refreshToken");			
			return authService.refreshToken(refreshToken);
		}
}