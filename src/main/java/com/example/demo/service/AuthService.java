package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.AuthResponseDTO;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.FingerprintUtil;
import com.example.demo.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private FingerprintUtil fingerprintUtil;

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
	public AuthResponseDTO login(String username, String password, String userAgent, String language, String timezone) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));

		String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());

		String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

		RefreshToken token = new RefreshToken();

		token.setUsername(username);

		token.setTokenHash(fingerprintUtil.hashRefreshToken(refreshToken));

		token.setUserAgentHash(fingerprintUtil.hashUserAgent(userAgent));

		token.setLanguageHash(fingerprintUtil.hashLanguage(language));

		token.setTimezoneHash(fingerprintUtil.hashTimezone(timezone));

		token.setRevoked(false);

		token.setExpiryDate(LocalDateTime.now().plusDays(7));

		refreshTokenRepository.save(token);

		return new AuthResponseDTO(accessToken, refreshToken);
	}

	// Refresh Access Token
	public AuthResponseDTO refreshToken(String refreshToken, String userAgent, String language, String timezone) {

		String refreshTokenHash = fingerprintUtil.hashRefreshToken(refreshToken);

		RefreshToken dbToken = refreshTokenRepository.findByTokenHash(refreshTokenHash)
				.orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));

		if (dbToken.isRevoked()) {

			throw new RuntimeException("Refresh Token Revoked");
		}

		if (dbToken.getExpiryDate().isBefore(LocalDateTime.now())) {

			dbToken.setRevoked(true);

			refreshTokenRepository.save(dbToken);

			throw new RuntimeException("Refresh Token Expired");
		}

		boolean fingerprintValid = fingerprintUtil.isFingerprintValid(

				dbToken.getUserAgentHash(), fingerprintUtil.hashUserAgent(userAgent),

				dbToken.getLanguageHash(), fingerprintUtil.hashLanguage(language),

				dbToken.getTimezoneHash(), fingerprintUtil.hashTimezone(timezone));

		if (!fingerprintValid) {

			dbToken.setRevoked(true);

			refreshTokenRepository.save(dbToken);

			throw new RuntimeException("Browser Fingerprint Validation Failed. Please Login Again.");
		}

		String username = dbToken.getUsername();

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));

		String newAccessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());

		return new AuthResponseDTO(newAccessToken, refreshToken);
	}

	// Logout
	public String logout(String refreshToken) {

		String refreshTokenHash = fingerprintUtil.hashRefreshToken(refreshToken);

		RefreshToken token = refreshTokenRepository.findByTokenHash(refreshTokenHash)
				.orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));

		token.setRevoked(true);

		refreshTokenRepository.save(token);

		return "Logged Out Successfully";
	}
}