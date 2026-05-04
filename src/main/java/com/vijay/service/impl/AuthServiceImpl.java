package com.vijay.service.impl;

import com.vijay.domain.UserRole;
import com.vijay.exception.UserException;
import com.vijay.modal.User;
import com.vijay.payload.dto.UserDto;
import com.vijay.payload.response.AuthResponse;
import com.vijay.repository.UserRepository;
import com.vijay.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(String username, String password) {
        return null;
    }

    @Override
    public AuthResponse signUp(UserDto req) throws UserException {

        User user = userRepository.findByEmail(req.getEmail());

        if(user == null){
            throw new UserException("email id already registered");
        }

        User createdUser = new User();
        createdUser.setEmail(req.getEmail());
        createdUser.setPassword(passwordEncoder.encode(req.getPassword()));
        createdUser.setPhone(req.getPhone());
        createdUser.setFullName(req.getFullName());
        createdUser.setLastLogin(LocalDateTime.now());
        createdUser.setRole(UserRole.ROLE_USER);

        User savedUser = userRepository.save(createdUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return null;
    }

    @Override
    public void createPasswordResetToken(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }
}
