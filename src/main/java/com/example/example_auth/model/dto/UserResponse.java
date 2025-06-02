package com.example.example_auth.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class UserResponse {

    private UUID id;
    private String username;
    private String email;
    private String[] roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}