package com.vijay.controller;


import com.vijay.exception.UserException;
import com.vijay.mapper.UserMapper;
import com.vijay.modal.User;
import com.vijay.payload.dto.UserDto;
import com.vijay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile() throws Exception {
        UserDto user = userService.getCurrentUser(); // ← UserDto not User
        return ResponseEntity.ok(user);
    }
}
