package com.akshat.trackops.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
}
