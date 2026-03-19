package com.starter.crud_springboot.service;

import com.starter.crud_springboot.dto.UserDTO;
import com.starter.crud_springboot.entity.User;
import com.starter.crud_springboot.mapper.UserMapper;
import com.starter.crud_springboot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO.GetUserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserDTO.GetUserDTO createUser(UserDTO.CreateUserDTO dto) {
        User user = UserMapper.toEntity(dto);

        // 🔐 Encodage ici uniquement
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return UserMapper.toDTO(userRepository.save(user));
    }

    public Optional<UserDTO.GetUserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    public Optional<UserDTO.GetUserDTO> updateUser(Long id, UserDTO.UpdateUserDTO dto) {
        return userRepository.findById(id)
                .map(user -> {

                    UserMapper.updateEntity(user, dto);

                    // 🔐 gérer le password ici uniquement
                    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                        user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    }

                    return UserMapper.toDTO(userRepository.save(user));
                });
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }
}