package com.example.final_project.security.service;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.User;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.UserRepository;
import com.example.final_project.security.jwt.JwtProvider;
import com.example.final_project.security.jwt.JwtRefreshRequest;
import com.example.final_project.security.jwt.JwtRequest;
import com.example.final_project.security.jwt.JwtResponse;
import com.example.final_project.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Data
public class AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final Mappers mapper;
    private final Map<String,String> refreshStorage = new HashMap<>();
    public JwtResponse login(UserLoginRequestDto request) {
        UserResponseDto userResponseDto = userService.loginUserProfile(request);
        final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
        final String refreshToken = jwtProvider.generateRefreshToken(userResponseDto);
        refreshStorage.put(userResponseDto.getEmail(),refreshToken);
        return new JwtResponse(accessToken,refreshToken);
    }


    public JwtResponse requestAccessToken(String jwt) {
        if(jwtProvider.validateRefreshToken(jwt)) {
            final Claims claims = jwtProvider.getRefreshTokenClaims(jwt);
            final String email = claims.getSubject();
            final String currentRefreshToken = refreshStorage.get(email);
            if(currentRefreshToken != null && jwt.equals(currentRefreshToken)) {
                UserResponseDto userDto = mapper.convertToUserResponseDto(userRepository.findByEmail(email).get());
                String accessToken = jwtProvider.generateAccessToken(userDto);
                return new JwtResponse(accessToken,null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse requestRefreshAndAccessToken(String jwt) {
        if(jwtProvider.validateRefreshToken(jwt)) {
            final Claims claims = jwtProvider.getRefreshTokenClaims(jwt);
            final String email = claims.getSubject();
            final String currentRefreshToken = refreshStorage.get(email);
            if(currentRefreshToken != null && jwt.equals(currentRefreshToken)) {
                UserResponseDto userDto = mapper.convertToUserResponseDto(userRepository.findByEmail(email).get());
                String accessToken = jwtProvider.generateAccessToken(userDto);
                String refreshToken = jwtProvider.generateRefreshToken(userDto);
                return new JwtResponse(accessToken,refreshToken);
            }
        }
        return new JwtResponse(null,null);
    }
}
