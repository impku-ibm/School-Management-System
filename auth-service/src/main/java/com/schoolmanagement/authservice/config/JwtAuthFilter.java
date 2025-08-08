package com.schoolmanagement.authservice.config;

import com.schoolmanagement.authservice.entity.User;
import com.schoolmanagement.authservice.repository.UserRepository;
import com.schoolmanagement.authservice.service.JwtService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter  {

   private final JwtService jwtService;
   private final UserRepository userRepository;

   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
      throws IOException, ServletException, java.io.IOException {

      final String authHeader = request.getHeader("Authorization");
      final String jwt;
      final String userEmail;

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
         filterChain.doFilter(request, response);
         return;
      }

      jwt = authHeader.substring(7);
      userEmail = jwtService.extractUsername(jwt);

      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
         User user = userRepository.findByEmail(userEmail).orElse(null);
         if (user != null && jwtService.isTokenValid(jwt, user.getEmail())) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
               user.getEmail(), null, null // no authorities yet
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
         }
      }

      filterChain.doFilter(request, response);
   }
}

