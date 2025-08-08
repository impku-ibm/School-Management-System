package com.schoolmanagement.authservice.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

   private final String SECRET_KEY = "super-secret-key-which-should-be-long-enough-for-hmac"; // Ideally store in config
   private final TokenBlacklistService tokenBlacklistService;

   public JwtService(TokenBlacklistService tokenBlacklistService) {
      this.tokenBlacklistService = tokenBlacklistService;
   }

   private Key getSignInKey() {
      return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
   }

   public String extractUsername(String token) {
      return extractClaim(token, Claims::getSubject);
   }

   public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
   }

   public String generateToken(String username) {
      Map<String, Object> claims = new HashMap<>();
      claims.put("roles", List.of("ROLE_ADMIN", "ROLE_TEACHER","ROLE_STUDENT"));
      return createToken(claims, username);
   }

   private String createToken(Map<String, Object> claims, String subject) {
      return Jwts.builder()
                 .setClaims(claims)
                 .setSubject(subject)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                 .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                 .compact();
   }

   public boolean isTokenValid(String token, String username) {
      final String extractedUsername = extractUsername(token);
      return (extractedUsername.equals(username)) && !isTokenExpired(token) && !tokenBlacklistService.isTokenBlacklisted(token);
   }

   private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
   }

   private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
   }

   private Claims extractAllClaims(String token) {
      return Jwts
                .parser()
                .verifyWith((javax.crypto.SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
   }
}

