package com.example.final_project.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String TYPE = "Bearer";
    private String accessToken;
    private String refreshToken;

}
