package com.maksimpegov.apigateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {

	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
	private static final Key SECRET_KEY = Keys.secretKeyFor(SIGNATURE_ALGORITHM);

	public String generateToken(String userId) {
		return Jwts.builder()
				.setSubject(userId)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24 hours
				.signWith(SECRET_KEY, SIGNATURE_ALGORITHM)
				.compact();
	}

	public String getUserId(String jwt) {
		return getClaims(jwt, Claims::getSubject);
	}

	public <T> T getClaims(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaims(token);
		return claimResolver.apply(claims);
	}

	public Boolean isTokenValid(String jwt) {
		try {
			return !isTokenExpired(jwt);
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isTokenExpired(String jwt) {
		return extractExpiration(jwt).before(new Date());
	}

	private Date extractExpiration(String jwt) {
		return getClaims(jwt, Claims::getExpiration);
	}

	private Claims getAllClaims(String jwt) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(jwt)
				.getBody();
	}
}
