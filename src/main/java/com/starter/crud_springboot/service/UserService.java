package com.starter.crud_springboot.service;

import com.starter.crud_springboot.dto.UserDTO;
import com.starter.crud_springboot.mapper.UserMapper;
import com.starter.crud_springboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO.GetUserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserDTO.GetUserDTO createUser(UserDTO.CreateUserDTO dto) {
        return UserMapper.toDTO(userRepository.save(UserMapper.toEntity(dto)));
    }

    public Optional<UserDTO.GetUserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }

    public Optional<UserDTO.GetUserDTO> updateUser(Long id, UserDTO.UpdateUserDTO dto) {
        return userRepository.findById(id)
                .map(user -> {
                    UserMapper.updateEntity(user, dto);
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