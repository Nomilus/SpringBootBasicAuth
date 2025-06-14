package com.example.example_auth.model.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character"
    )
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}