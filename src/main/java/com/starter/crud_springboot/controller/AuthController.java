package com.starter.crud_springboot.controller;

import com.starter.crud_springboot.dto.AuthDTO;
import com.starter.crud_springboot.dto.UserDTO;
import com.starter.crud_springboot.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthDTO.LoginResponse login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDTO.RegisterResponse> register(@Valid @RequestBody UserDTO.CreateUserDTO request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }
}