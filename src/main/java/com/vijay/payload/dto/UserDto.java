package com.vijay.payload.dto;

import com.vijay.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private String fullName;
    private UserRole role;
    private String phone;
    private String userName;
    private LocalDateTime lastLogin;


}
