package com.starter.crud_springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDTO {

    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {}

    public record LoginResponse(
            String token,
            String email
    ) {}

    public record RegisterResponse(
            Long id,
            String name,
            String email
    ) {}
}