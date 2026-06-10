package com.akshat.trackops.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.akshat.trackops.auth.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/login","/api/auth/users/signup").permitAll().anyRequest().authenticated())
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
