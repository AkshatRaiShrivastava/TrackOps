package com.akshat.trackops.user.service;

import com.akshat.trackops.user.dto.CreateUserRequest;
import com.akshat.trackops.user.dto.UserResponse;

import java.util.List;


public interface UserService {
    UserResponse createUser(CreateUserRequest request) throws Exception;
    List<UserResponse> getAllUsers();
    UserResponse deleteUser(Long id) throws Exception;
}
