package com.vijay.service;

import com.vijay.exception.UserException;
import com.vijay.payload.dto.UserDto;
import com.vijay.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String username, String password) throws UserException;
    AuthResponse signUp(UserDto req) throws UserException;
    void createPasswordResetToken(String email) throws UserException;
    void resetPassword(String token, String newPassword);
}
