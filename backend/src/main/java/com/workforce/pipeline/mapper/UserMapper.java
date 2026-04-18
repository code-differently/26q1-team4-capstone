package com.workforce.pipeline.mapper;

import com.workforce.pipeline.dto.UserDTO;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.enums.UserRole;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    // ----------------------------
    // ENTITY → DTO
    // ----------------------------
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        // enum → string
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);

        // flatten skills safely
        dto.setSkills(user.getSkills());

        return dto;
    }

    // ----------------------------
    // DTO → ENTITY
    // ----------------------------
    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        // string → enum (SAFE parsing)
        if (dto.getRole() != null) {
            user.setRole(UserRole.valueOf(dto.getRole()));
        }

        return user;
    }
}