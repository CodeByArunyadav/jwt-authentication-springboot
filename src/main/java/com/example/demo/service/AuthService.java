package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

		// Encrypt Password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (user.getRole() == null) {
			user.setRole("USER");
		}

		userRepository.save(user);

		return "User Registered Successfully";
	}

	public String login(String username, String password) {

		authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(username, password));

		return jwtUtil.generateToken(username);
	}
}