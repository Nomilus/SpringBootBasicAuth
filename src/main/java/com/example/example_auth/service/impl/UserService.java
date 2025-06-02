package com.example.example_auth.service.impl;

import com.example.example_auth.model.dto.UserRequest;
import com.example.example_auth.model.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(UUID id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(UUID id, UserRequest userRequest);

    void deleteUser(UUID id);
}