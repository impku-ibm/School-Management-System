package com.schoolmanagement.teacherservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthFilter jwtAuthFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/teachers").hasAnyRole("ADMIN", "TEACHER") // ADMIN and TEACHER can view all
                .requestMatchers(HttpMethod.GET, "/teachers/**").hasAnyRole("ADMIN", "TEACHER") // ADMIN and TEACHER can view details
                .requestMatchers(HttpMethod.POST, "/teachers").hasRole("ADMIN") // Only ADMIN can create
                .requestMatchers(HttpMethod.PUT, "/teachers/**").hasRole("ADMIN") // Only ADMIN can update
                .requestMatchers(HttpMethod.DELETE, "/teachers/**").hasRole("ADMIN") // Only ADMIN can delete
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}