package com.starter.crud_springboot.mapper;

import com.starter.crud_springboot.dto.UserDTO;
import com.starter.crud_springboot.entity.User;

public class UserMapper {
    // ✅ Entity -> DTO
    public static UserDTO.GetUserDTO toDTO(User user) {
        return new UserDTO.GetUserDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // ✅ CreateDTO -> Entity
    public static User toEntity(UserDTO.CreateUserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    // ✅ UpdateDTO -> EXISTING Entity
    public static void updateEntity(User user, UserDTO.UpdateUserDTO dto) {

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
    }
}
