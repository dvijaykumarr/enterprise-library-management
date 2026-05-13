package com.vijay.service.impl;

import com.vijay.exception.UserException;
import com.vijay.mapper.UserMapper;
import com.vijay.modal.User;
import com.vijay.payload.dto.UserDto;
import com.vijay.repository.UserRepository;
import com.vijay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public User getCurrentUserEntity() throws Exception {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email);
        if (user == null) throw new UserException("User not found");
        return user;
    }


    @Override
    public UserDto getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }
        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();


        return users.stream().map(
                UserMapper::toDTO
        ).collect(Collectors.toList());
    }
}
