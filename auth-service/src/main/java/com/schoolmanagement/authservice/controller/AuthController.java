package com.schoolmanagement.authservice.controller;

import com.schoolmanagement.authservice.dto.AuthResponse;
import com.schoolmanagement.authservice.dto.LoginRequest;
import com.schoolmanagement.authservice.dto.RegisterRequest;
import com.schoolmanagement.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

   private final AuthService authService;

   @PostMapping("/register")
   public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
      AuthResponse response = authService.register(request);
      return ResponseEntity.ok(response);
   }


   @PostMapping("/login")
   public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
      AuthResponse response = authService.login(request);
      return ResponseEntity.ok(response);
   }

   @PostMapping("/logout")
   public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
      String token = authHeader.substring(7); // Remove "Bearer " prefix
      String message = authService.logout(token);
      return ResponseEntity.ok(message);
   }
}
