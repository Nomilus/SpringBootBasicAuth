package com.example.example_auth.controller;

import com.example.example_auth.model.dto.req.UserRequest;
import com.example.example_auth.model.dto.res.MainResponse;
import com.example.example_auth.model.dto.res.UserResponse;
import com.example.example_auth.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<MainResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse createdUser = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MainResponse<>(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Created successfully",
                "/api/users",
                createdUser
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainResponse<UserResponse>> getUserById(@PathVariable("id") UUID uuid) {
        UserResponse user = userService.getUserById(uuid);
        return ResponseEntity.ok(new MainResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Successfully",
                "/api/users/" + uuid,
                user
        ));
    }

    @GetMapping
    public ResponseEntity<MainResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(new MainResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Successfully",
                "/api/users",
                users
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MainResponse<UserResponse>> updateUser(@PathVariable("id") UUID uuid, @Valid @RequestBody UserRequest userRequest) {
        UserResponse user = userService.updateUser(uuid, userRequest);
        return ResponseEntity.ok(new MainResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Update successfully",
                "/api/users/" + uuid,
                user
        ));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MainResponse<UserResponse>> deleteUser(@PathVariable("id") UUID uuid) {
        userService.deleteUser(uuid);
        return ResponseEntity.ok(new MainResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Delete successfully",
                "/api/users/" + uuid,
                null
        ));
    }
}