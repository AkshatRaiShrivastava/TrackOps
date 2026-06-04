package com.akshat.trackops.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshat.trackops.auth.dto.LoginRequest;
import com.akshat.trackops.auth.dto.LoginResponse;
import com.akshat.trackops.auth.service.AuthService;
import com.akshat.trackops.common.ApiResponse;

@RestController
@RequestMapping("/api")
public class AuthController {
    AuthService service;
    public AuthController(AuthService service){
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request){
        try{
            LoginResponse response = service.login(request);
            return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse<>(true, "Login successful", response));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
