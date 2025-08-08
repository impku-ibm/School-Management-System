package com.schoolmanagement.authservice.service;

import com.schoolmanagement.authservice.dto.AuthResponse;
import com.schoolmanagement.authservice.dto.LoginRequest;
import com.schoolmanagement.authservice.dto.RegisterRequest;
import com.schoolmanagement.authservice.entity.User;
import com.schoolmanagement.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final JwtService jwtService;
   private final TokenBlacklistService tokenBlacklistService;

   public AuthResponse register(RegisterRequest request) {
      boolean exists = userRepository.existsByEmail(request.getEmail());
      if (exists) {
         throw new RuntimeException("Email already in use");
      }

      User user = User.builder()
                      .email(request.getEmail())
                      .password(passwordEncoder.encode(request.getPassword()))
                      .fullName(request.getFullName())
                      .build();

      userRepository.save(user);
      String token = jwtService.generateToken(user.getEmail());
      return new AuthResponse(token);
   }

   public AuthResponse login(LoginRequest request) {
      User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

      if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
         throw new RuntimeException("Invalid email or password");
      }

      String token = jwtService.generateToken(user.getEmail());
      return new AuthResponse(token);
   }

   public String logout(String token) {
      tokenBlacklistService.blacklistToken(token);
      return "Logged out successfully";
   }
}
