package com.event.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    public String generateToke(String username){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims().add(claims).subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 30 * 1000 ))
                .and()
                .signWith(generateKey()).compact();
    }

    private SecretKey generateKey() throws RuntimeException {
        SecretKey key = new SecretKeySpec("asdfghjk345678dfghj3456788uhsdfsdfsfdb".getBytes(), "HmacSHA256");
        String keyString = Base64.getEncoder().encodeToString(key.getEncoded());
        byte[] keyByte = Decoders.BASE64.decode(keyString);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        System.out.println("*********"+username);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
