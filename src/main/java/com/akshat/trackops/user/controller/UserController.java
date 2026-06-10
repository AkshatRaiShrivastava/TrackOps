package com.akshat.trackops.user.controller;

import com.akshat.trackops.common.ApiResponse;
import com.akshat.trackops.user.dto.CreateUserRequest;
import com.akshat.trackops.user.dto.UserResponse;
import com.akshat.trackops.user.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody CreateUserRequest request){
        try{
            UserResponse response = userService.createUser(request);

            if (response != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User created successfully", response));
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "User not created", response));
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers(){
        return  userService.getAllUsers();
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(@PathVariable Long id) {
        try{

            UserResponse response = userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse<>(true, "User deleted successfully", response));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
