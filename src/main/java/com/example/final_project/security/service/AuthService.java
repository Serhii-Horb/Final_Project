package com.example.final_project.security.service;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.User;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.repository.UserRepository;
import com.example.final_project.security.jwt.JwtProvider;
import com.example.final_project.security.jwt.JwtRequest;
import com.example.final_project.security.jwt.JwtResponse;
import com.example.final_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final Map<String,String> refreshStorage = new HashMap<>();
    public JwtResponse login(UserLoginRequestDto request) {
        UserResponseDto userResponseDto = userService.loginUserProfile(request);
        final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
        final String refreshToken = jwtProvider.generateRefreshToken(userResponseDto);
        refreshStorage.put(userResponseDto.getEmail(),refreshToken);
        return new JwtResponse(accessToken,refreshToken);
    }
}
