package com.maksimpegov.users.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET_KEY = "A87BDAE5D4DAB6FFF72729EE1D82D";

    public String generateToken(String userId) {

        return Jwts
                .builder()
//                .setClaims(Map.of("userId", userId)) if we wat to add extra claims
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60000 * 60 * 24))
                .signWith(getSignIngKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    public String getUserId(String jwt) {
        return getClaims(jwt, Claims::getSubject);
    }

    public <T> T getClaims(String token, Function<Claims, T> claimResolver){
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Boolean isTokenValid(String jwt, String userId) {
        String tokenUserId = getUserId(jwt);
        return tokenUserId.equals(userId) && isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return getClaims(jwt, Claims::getExpiration);
    }

    private Claims getAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey("getSignIngKey()")
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSignIngKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
