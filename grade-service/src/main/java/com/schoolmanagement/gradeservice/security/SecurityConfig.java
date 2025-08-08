package com.schoolmanagement.gradeservice.security;

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
                .requestMatchers(HttpMethod.GET, "/grades/student/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT") // Students can view own grades
                .requestMatchers(HttpMethod.GET, "/grades/**").hasAnyRole("ADMIN", "TEACHER") // ADMIN and TEACHER can view all
                .requestMatchers(HttpMethod.POST, "/grades").hasAnyRole("ADMIN", "TEACHER") // ADMIN and TEACHER can create
                .requestMatchers(HttpMethod.PUT, "/grades/**").hasAnyRole("ADMIN", "TEACHER") // ADMIN and TEACHER can update
                .requestMatchers(HttpMethod.DELETE, "/grades/**").hasRole("ADMIN") // Only ADMIN can delete
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}