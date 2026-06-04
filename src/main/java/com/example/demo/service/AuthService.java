package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.AuthResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

// Register User
	public String register(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (user.getRole() == null) {
			user.setRole("USER");
		}

		userRepository.save(user);

		return "User Registered Successfully";
	}

// Login
	public AuthResponseDTO login(String username, String password) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));

		String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());

		String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

		return new AuthResponseDTO(accessToken, refreshToken);
	}

// Refresh Access Token
	public AuthResponseDTO refreshToken(String refreshToken) {

		if (!jwtUtil.validateRefreshToken(refreshToken)) {

			throw new RuntimeException("Invalid Refresh Token");
		}

		String username = jwtUtil.extractUsername(refreshToken);

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));

		String newAccessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());

		return new AuthResponseDTO(newAccessToken, refreshToken);
	}

}
