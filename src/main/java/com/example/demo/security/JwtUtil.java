package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            "mySuperSecretKeyForJwtAuthentication123456";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes()
            );

    // Generate Token
    public String generateToken(
            String username) {

        return Jwts.builder()

                .subject(username)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                + 86400000
                        )
                )

                .signWith(
                        key,
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    // Extract Username
    public String extractUsername(
            String token) {

        return getClaims(token)
                .getSubject();
    }

    // Validate Token
    public boolean validateToken(
            String token,
            String username) {

        try {

            String extractedUsername =
                    extractUsername(token);

            return extractedUsername
                    .equals(username)

                    && !isTokenExpired(token);

        } catch (Exception e) {

            return false;
        }
    }

    // Check Expiration
    private boolean isTokenExpired(
            String token) {

        return getClaims(token)

                .getExpiration()

                .before(new Date());
    }

    // Read Claims
    private Claims getClaims(
            String token) {

        return Jwts.parser()

                .verifyWith(key)

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }
}