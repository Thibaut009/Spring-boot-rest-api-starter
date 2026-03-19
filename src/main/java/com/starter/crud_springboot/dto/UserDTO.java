package com.starter.crud_springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDTO {

    public record GetUserDTO(
            Long id,
            String name,
            String email
    ) {}

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateUserDTO {

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
        private String name;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateUserDTO {

        @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
        private String name;

        @Email(message = "Invalid email format")
        private String email;

        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;
    }
}