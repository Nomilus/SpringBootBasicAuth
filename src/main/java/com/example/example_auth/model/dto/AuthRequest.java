// File: AuthRequest.java
package com.example.example_auth.model.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}