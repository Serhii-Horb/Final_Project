package com.example.final_project.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    public static  JwtAuthentication generate(Claims tokenClaims) {
        String username = tokenClaims.getSubject();
        String role = tokenClaims.get("role",String.class);
        return new JwtAuthentication(username,role);
    }
}
