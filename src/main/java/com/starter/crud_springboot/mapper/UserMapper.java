package com.starter.crud_springboot.mapper;

import com.starter.crud_springboot.dto.UserDTO;
import com.starter.crud_springboot.entity.User;

public class UserMapper {

    // ✅ Entity -> DTO
    public static UserDTO.GetUserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO.GetUserDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // ✅ CreateDTO -> Entity
    public static User toEntity(UserDTO.CreateUserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        // ❌ PAS de password ici
        return user;
    }

    // ✅ UpdateDTO -> EXISTING Entity
    public static void updateEntity(User user, UserDTO.UpdateUserDTO dto) {

        if (user == null || dto == null) {
            return;
        }

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }

        // ❌ PAS de password ici non plus
    }
}