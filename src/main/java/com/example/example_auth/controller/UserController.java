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
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<MainResponse<UserResponse>> register(@Valid @RequestBody UserRequest userRequest) {
        UserResponse createdUser = userService.register(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MainResponse<>(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Created successfully",
                "/api/register",
                createdUser
        ));
    }

    @GetMapping("/private")
    public ResponseEntity<MainResponse<UserResponse>> getDetailUserById(@RequestAttribute("authenticatedUser") UUID uuid) {
        UserResponse user = userService.getDetailUserById(uuid);
        return ResponseEntity.ok(new MainResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Successfully",
                "/api/private/" + uuid,
                user
        ));
    }

    @PutMapping("/private")
    public ResponseEntity<MainResponse<UserResponse>> updateUser(@RequestAttribute("authenticatedUser") UUID uuid, @Valid @RequestBody UserRequest userRequest) {
        UserResponse user = userService.updateUser(uuid, userRequest);
        return ResponseEntity.ok(new MainResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Update successfully",
                "/api/private/" + uuid,
                user
        ));

    }

    @DeleteMapping("/private")
    public ResponseEntity<MainResponse<UserResponse>> deleteUser(@RequestAttribute("authenticatedUser") UUID uuid) {
        userService.deleteUser(uuid);
        return ResponseEntity.ok(new MainResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Delete successfully",
                "/api/private/" + uuid,
                null
        ));
    }
}