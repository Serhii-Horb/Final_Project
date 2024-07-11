package com.example.final_project.security.controller;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.security.jwt.JwtResponse;
import com.example.final_project.security.service.AuthService;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserLoginRequestDto request) {
        final JwtResponse token = authService.login(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
