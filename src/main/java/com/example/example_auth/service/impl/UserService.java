package com.example.example_auth.service.impl;

import com.example.example_auth.model.dto.req.UserRequest;
import com.example.example_auth.model.dto.res.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse register(UserRequest userRequest);

    UserResponse getDetailUserById(UUID id);

    UserResponse updateUser(UUID id, UserRequest userRequest);

    void deleteUser(UUID id);
}