package com.example.final_project.security.jwt;

import lombok.Data;

@Data
public class JwtRefreshRequest {
    private String refreshToken;
}
