package com.starter.crud_springboot.service;

import com.starter.crud_springboot.dto.UserDTO;
import com.starter.crud_springboot.entity.User;
import com.starter.crud_springboot.exception.EmailAlreadyInUseException;
import com.starter.crud_springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public List<UserDTO.GetUserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO.GetUserDTO::new)
                .toList();
    }

    public UserDTO.GetUserDTO createUser(UserDTO.CreateUserDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException(dto.getEmail());
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return new UserDTO.GetUserDTO(userRepository.save(user));
    }

    public Optional<UserDTO.GetUserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO.GetUserDTO::new);
    }

    public Optional<UserDTO.GetUserDTO> updateUser(Long id, UserDTO.UpdateUserDTO dto) {
        return userRepository.findById(id)
                .map(user -> {
                    if (dto.getName() != null && !dto.getName().isBlank()) user.setName(dto.getName());
                    if (dto.getEmail() != null && !dto.getEmail().isBlank()) user.setEmail(dto.getEmail());
                    if (dto.getPassword() != null && !dto.getPassword().isBlank()) user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    return new UserDTO.GetUserDTO(userRepository.save(user));
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