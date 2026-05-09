package com.vijay.payload.dto;

import com.vijay.domain.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotNull(message = "email is required")
    private String email;

    @NotNull(message = "password is required")
    private String password;

    @NotNull(message = "fullName is required")
    private String fullName;
    private UserRole role;
    private String phone;
    private String userName;
    private LocalDateTime lastLogin;


}
