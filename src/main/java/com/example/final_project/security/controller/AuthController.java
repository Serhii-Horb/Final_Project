package com.example.final_project.security.controller;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.requestDto.UserRegisterRequestDto;
import com.example.final_project.exceptions.AuthorizationException;
import com.example.final_project.security.jwt.JwtRequestRefresh;
import com.example.final_project.security.jwt.JwtResponse;
import com.example.final_project.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authorization controller.", description = "User registration, login and receipt of security tokens are done using this controller.")
public class AuthController {

    /**
     * The authentication service bean for handling authentication operations.
     */
    private final AuthService authService;

    /**
     * Handles user login and returns a JWT response.
     *
     * @param userLoginRequestDto the authentication request containing username and password.
     * @return a ResponseEntity containing the JWT response.
     * @throws AuthException if authentication fails.
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "User login.",
            description = "Allows a user to log in by providing email and password."
    )
    public ResponseEntity<JwtResponse> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        final JwtResponse token = authService.login(userLoginRequestDto);
        return ResponseEntity.ok(token);
    }

    /**
     * Obtains a new access token using a refresh token.
     *
     * @param request the request containing the refresh token.
     * @return a ResponseEntity containing the JWT response.
     * @throws AuthException if token refresh fails.
     */
    @Operation(
            summary = "Receiving a new access token.",
            description = "Allows you to get a new access token for the user."
    )
    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtRequestRefresh request) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    /**
     * Obtains a new refresh token using an existing refresh token.
     *
     * @param request the request containing the refresh token.
     * @return a ResponseEntity containing the JWT response.
     * @throws AuthException if token refresh fails.
     */
    @PostMapping("/refresh")
    @Operation(
            summary = "Receiving a new refresh token.",
            description = "Allows you to get a new refresh token for the user."
    )
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody JwtRequestRefresh request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(
            summary = "User registration.",
            description = "Allows you to register a new user."
    )
    public ResponseEntity<JwtResponse> register(@RequestBody UserRegisterRequestDto userRegisterRequestDto) throws AuthorizationException {
        JwtResponse token = authService.createUser(userRegisterRequestDto);
        return ResponseEntity.ok(token);
    }
}