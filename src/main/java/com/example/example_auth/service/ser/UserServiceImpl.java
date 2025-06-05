package com.example.example_auth.service.ser;

import com.example.example_auth.exception.custom.DuplicateUserException;
import com.example.example_auth.exception.custom.UserNotFoundException;
import com.example.example_auth.mapper.UserMapper;
import com.example.example_auth.model.dto.req.UserRequest;
import com.example.example_auth.model.dto.res.UserResponse;
import com.example.example_auth.model.entity.User;
import com.example.example_auth.repository.UserRepository;
import com.example.example_auth.service.impl.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateUserException("Username already exists");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateUserException("Email already exists");
        }
        User user = userMapper.toEntity(userRequest);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(UUID id) {
        return userMapper.toResponse(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id)));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id:" + id));
        userMapper.updateEntity(userRequest, existingUser);
        return userMapper.toResponse(userRepository.save(existingUser));
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id:" + id)));
    }
}
