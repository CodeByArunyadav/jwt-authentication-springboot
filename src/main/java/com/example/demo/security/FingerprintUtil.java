package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class FingerprintUtil {

	/**
	 * SHA-256 hash
	 */
	public String hash(String value) {

		if (value == null) {
			value = "";
		}

		try {

			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte[] hashBytes = md.digest(value.getBytes(StandardCharsets.UTF_8));

			StringBuilder sb = new StringBuilder();

			for (byte b : hashBytes) {

				sb.append(String.format("%02x", b));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {

			throw new RuntimeException("Unable to generate hash", e);
		}
	}

	/**
	 * Hash refresh token before storing in DB
	 */
	public String hashRefreshToken(String refreshToken) {

		return hash(refreshToken);
	}

	/**
	 * Hash User-Agent
	 */
	public String hashUserAgent(String userAgent) {

		return hash(userAgent);
	}

	/**
	 * Hash Accept-Language
	 */
	public String hashLanguage(String language) {

		return hash(language);
	}

	/**
	 * Hash Timezone
	 */
	public String hashTimezone(String timezone) {

		return hash(timezone);
	}

	/**
	 * Weighted Fingerprint Validation
	 *
	 * User-Agent = 1 Language = 1 TimeZone = 2
	 *
	 * Score >= 3 => Valid
	 */
	public int calculateFingerprintScore(String storedUserAgentHash, String currentUserAgentHash,
			String storedLanguageHash, String currentLanguageHash, String storedTimezoneHash,
			String currentTimezoneHash) {

		int score = 0;

		if (storedUserAgentHash.equals(currentUserAgentHash)) {

			score += 1;
		}

		if (storedLanguageHash.equals(currentLanguageHash)) {

			score += 1;
		}

		if (storedTimezoneHash.equals(currentTimezoneHash)) {

			score += 2;
		}
            //System.out.println("total score is ="+score);
		return score;
	}

	/**
	 * Score >= 3 => Allow Score < 3 => Revoke Token
	 */
	public boolean isFingerprintValid(String storedUserAgentHash, String currentUserAgentHash,
			String storedLanguageHash, String currentLanguageHash, String storedTimezoneHash,
			String currentTimezoneHash) {

		return calculateFingerprintScore(storedUserAgentHash, currentUserAgentHash, storedLanguageHash,
				currentLanguageHash, storedTimezoneHash, currentTimezoneHash) >= 4;
	}
}