package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	// Register User
	public String register(User user) {

		// Encrypt Password
		user.setPassword(

				passwordEncoder.encode(user.getPassword()));

		userRepository.save(user);

		return "User Registered Successfully";
	}

	// Login User
	public String login(String username, String password) {

		User user = userRepository

				.findByUsername(username)

				.orElseThrow(() -> new RuntimeException("User Not Found"));

		// Validate Password
		boolean validPassword =

				passwordEncoder.matches(password, user.getPassword());

		if (!validPassword) {

			throw new RuntimeException("Invalid Password");
		}

		// Generate JWT
		return jwtUtil.generateToken(username);
	}
}