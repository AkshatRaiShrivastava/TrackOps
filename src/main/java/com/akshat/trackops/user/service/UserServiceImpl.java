package com.akshat.trackops.user.service;

import com.akshat.trackops.user.dto.CreateUserRequest;
import com.akshat.trackops.user.dto.UserResponse;
import com.akshat.trackops.user.entity.Role;
import com.akshat.trackops.user.entity.User;
import com.akshat.trackops.user.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    UserServiceImpl(UserRepository repo){
        this.userRepo = repo;
        this.encoder = new BCryptPasswordEncoder();
    }
    @Override
    public UserResponse createUser(CreateUserRequest request) throws Exception {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new Exception("User already exists with this mail id");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(Role.valueOf(request.getRole()));
        user.setPassword(encoder.encode(request.getPassword()));
        User savedUser = userRepo.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setName(savedUser.getName());
        response.setRole(savedUser.getRole().name());

        return response;
    }

    @Override
    public List<UserResponse> getAllUsers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size,Sort.by(sortBy));
        List<UserResponse> list = new ArrayList<>();
        Page<User> users = userRepo.findAll(pageable);
        for (User user:users){
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole().toString());
            list.add(response);
        }
        return list;
    }

    @Override
    public UserResponse deleteUser(Long id) throws Exception {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()){
            UserResponse response = new UserResponse();
            response.setName(user.get().getName());
            response.setEmail(user.get().getEmail());
            response.setId(user.get().getId());
            response.setRole(user.get().getRole().toString());
            userRepo.deleteById(id);
            return response;
        }
        else{
            throw new Exception("User not found");
        }
    }
}


























