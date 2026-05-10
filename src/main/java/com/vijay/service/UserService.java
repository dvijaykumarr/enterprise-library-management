package com.vijay.service;

import com.vijay.exception.UserException;
import com.vijay.modal.User;
import com.vijay.payload.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    public UserDto getCurrentUser() throws Exception, UserException;
    public List<UserDto> getAllUsers();
}
