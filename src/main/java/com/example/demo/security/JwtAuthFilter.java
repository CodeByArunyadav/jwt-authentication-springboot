package com.example.demo.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(

			HttpServletRequest request,

			HttpServletResponse response,

			FilterChain filterChain)

			throws ServletException, IOException {

		// Read Authorization Header
		String authHeader = request.getHeader("Authorization");

		String token = null;

		String username = null;

		// Check Bearer Token
		if (authHeader != null && authHeader.startsWith("Bearer ")) {

			token = authHeader.substring(7);

			try {

				// Extract Username
				username = jwtUtil.extractUsername(token);

			} catch (Exception e) {

				System.out.println("Invalid JWT Token");
			}
		}

		// Validate Token
		if (username != null

				&& SecurityContextHolder.getContext().getAuthentication() == null) {

			if (jwtUtil.validateToken(token, username)) {

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(

						username, null, null);

				authToken.setDetails(

						new WebAuthenticationDetailsSource().buildDetails(request));

				// Set Authentication
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// Continue Request
		filterChain.doFilter(request, response);
	}
}