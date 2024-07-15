package com.example.final_project.security.jwt;

import lombok.Data;
import lombok.Getter;

@Data
public class JwtRefreshRequest {
    private String refreshToken;

}
