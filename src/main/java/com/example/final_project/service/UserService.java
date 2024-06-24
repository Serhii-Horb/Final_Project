package com.example.final_project.service;

import com.example.final_project.dto.UserDto;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CartRepository;
import com.example.final_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final Mappers mappers;

    public UserDto registerUser(UserDto userDto) {
      return null;
    }
}
