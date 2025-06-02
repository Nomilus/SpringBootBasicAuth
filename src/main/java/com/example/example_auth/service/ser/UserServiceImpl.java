package com.example.example_auth.service.ser;

import com.example.example_auth.exception.UserNotFoundException;
import com.example.example_auth.mapper.UserMapper;
import com.example.example_auth.model.dto.PasswordChangeRequest;
import com.example.example_auth.model.dto.UserRequest;
import com.example.example_auth.model.dto.UserResponse;
import com.example.example_auth.model.entity.User;
import com.example.example_auth.repository.UserRepository;
import com.example.example_auth.service.impl.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private User user;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(UUID id) {
        return userMapper.toResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id:" + id)));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    // Add to UserServiceImpl.java
    @Override
    public UserResponse getUserByUsername(String username) {
        return userMapper.toResponse(userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id:" + id));

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        userMapper.updateEntity(userRequest, existingUser);
        return userMapper.toResponse(userRepository.save(existingUser));
    }

    @Override
    public void changePassword(UUID userId, PasswordChangeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id:" + id)));
    }
}
