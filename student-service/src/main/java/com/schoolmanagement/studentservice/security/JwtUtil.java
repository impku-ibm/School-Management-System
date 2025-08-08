package com.schoolmanagement.studentservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtUtil {
   private final String SECRET = "super-secret-key-which-should-be-long-enough-for-hmac";

   private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

   private Key getSignInKey() {
      return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
   }

   public String extractUsername(String token) {
      return extractClaims(token).getSubject();
   }

   public boolean validateToken(String token) {
      try {
         extractClaims(token);
         return true;
      } catch (JwtException e) {
         return false;
      }
   }

   public Claims extractClaims(String token) {
      return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
   }
}
