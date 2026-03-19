package com.starter.crud_springboot.service;

import com.starter.crud_springboot.dto.AuthDTO;
import com.starter.crud_springboot.dto.UserDTO;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthDTO.LoginResponse login(AuthDTO.LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(request.email());

        return new AuthDTO.LoginResponse(token, request.email());
    }

    public AuthDTO.RegisterResponse register(UserDTO.CreateUserDTO request) {
        UserDTO.GetUserDTO created = userService.createUser(request);

        return new AuthDTO.RegisterResponse(created.id(), created.name(), created.email());
    }
}