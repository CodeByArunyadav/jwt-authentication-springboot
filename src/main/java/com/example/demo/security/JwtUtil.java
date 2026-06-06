package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "mySuperSecretKeyForJwtAuthentication123456";

	private SecretKey getSigningKey() {

		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	// Generate Access Token (15 Minutes)
	public String generateAccessToken(String username, String role) {

		return Jwts.builder().subject(username).claim("role", role).claim("type", "ACCESS").issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + (15 * 60 * 1000))).signWith(getSigningKey())
				.compact();
	}

	// Generate Refresh Token (7 Days)
	public String generateRefreshToken(String username) {

		return Jwts.builder().subject(username).claim("type", "REFRESH").claim("jti", UUID.randomUUID().toString())
				.issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)))
				.signWith(getSigningKey()).compact();
	}

	// Extract Username
	public String extractUsername(String token) {

		return extractAllClaims(token).getSubject();
	}

	// Extract Role
	public String extractRole(String token) {

		return extractAllClaims(token).get("role", String.class);
	}

	// Extract Token Type
	public String extractTokenType(String token) {

		return extractAllClaims(token).get("type", String.class);
	}

	// Validate Access Token
	public boolean validateToken(String token, String username) {

		try {

			String extractedUsername = extractUsername(token);

			String tokenType = extractTokenType(token);

			return extractedUsername.equals(username) && "ACCESS".equals(tokenType) && !isTokenExpired(token);

		} catch (Exception e) {

			return false;
		}
	}

	// Validate Refresh Token
	public boolean validateRefreshToken(String token) {

		try {

			return "REFRESH".equals(extractTokenType(token)) && !isTokenExpired(token);

		} catch (Exception e) {

			return false;
		}
	}

	// Check Token Expiry
	private boolean isTokenExpired(String token) {

		return extractAllClaims(token).getExpiration().before(new Date());
	}

	// Extract All Claims
	private Claims extractAllClaims(String token) {

		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}
}