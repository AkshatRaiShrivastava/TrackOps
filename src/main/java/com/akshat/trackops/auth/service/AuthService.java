package com.akshat.trackops.auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.akshat.trackops.auth.dto.LoginRequest;
import com.akshat.trackops.auth.dto.LoginResponse;
import com.akshat.trackops.auth.security.JwtService;
import com.akshat.trackops.user.entity.User;
import com.akshat.trackops.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;
    

    public LoginResponse login(LoginRequest request) throws Exception {
        Optional<User> user = repo.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with this email id does not exist");
        }
        
        if (!encoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new Exception("Invalid password");
        }

        LoginResponse response = new LoginResponse();
        response.setId(user.get().getId());
        response.setName(user.get().getName());
        response.setEmail(user.get().getEmail());
        response.setRole(user.get().getRole().name());
        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }
}
