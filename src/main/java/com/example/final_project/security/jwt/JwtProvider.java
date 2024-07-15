package com.example.final_project.security.jwt;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.exceptions.AuthorizationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    public String generateAccessToken(@NotNull UserResponseDto userDto) {
        final Instant accessTokenExpInstant = LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessTokenExpInstant);
        return Jwts.builder()
                .setSubject(userDto.getEmail())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("role",userDto.getRole())
                .claim("name",userDto.getName())
                .compact();
    }
    public String generateRefreshToken(@NotNull UserResponseDto userDto) {
        final Instant refreshTokenExpInstant = LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshTokenExpInstant);
        return Jwts.builder()
                .setSubject(userDto.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }
    public boolean validateAccessToken(@NotNull String accessToken) {
        return validateToken(accessToken,jwtAccessSecret);
    }
    public boolean validateRefreshToken(@NotNull String refreshToken) {
        return validateToken(refreshToken,jwtRefreshSecret);
    }

    private boolean validateToken(String token, @NotNull Key secretKey) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expJwtEx) {
            throw new AuthorizationException("Token is expired");
        } catch (UnsupportedJwtException unsJwtEx) {
            throw new AuthorizationException("Jwt is unsupported");
        } catch (MalformedJwtException malJwtEx) {
            throw new AuthorizationException("Jwt is malformed");
        } catch (SignatureException sigEx) {
            throw new AuthorizationException("Signature is invalid");
        } catch (Exception ex) {
            throw new AuthorizationException("Invalid token");
        }
    }
    public Claims getAccessTokenClaims(@NotNull String accessToken) {
        return getClaims(accessToken,jwtAccessSecret);
    }

    public Claims getRefreshTokenClaims(@NotNull String refreshToken) {
        return getClaims(refreshToken,jwtRefreshSecret);
    }
    public Claims getClaims(String token,@NotNull Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
