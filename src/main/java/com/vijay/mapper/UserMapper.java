package com.vijay.mapper;

import com.vijay.modal.User;
import com.vijay.payload.dto.UserDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// FIXED: removed @Service - mappers should not be Spring beans, same as BookMapper and GenreMapper
public class UserMapper {

    public static UserDto toDTO(User user) {
        if (user == null) return null; // FIXED: added null check

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setPhone(user.getPhone());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public static List<UserDto> toDTOList(List<User> users) {
        if (users == null || users.isEmpty()) return List.of(); // FIXED: added null check
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Set<UserDto> toDTOSet(Set<User> users) {
        if (users == null || users.isEmpty()) return Set.of(); // FIXED: added null check
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public static User toEntity(UserDto userDto) { // FIXED: Static → static (lowercase)
        if (userDto == null) return null;

        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());
        // Note: password is handled in service layer with encoding
        // Note: authProvider, lastLogin, createdAt, updatedAt managed separately
        return user;
    }
}