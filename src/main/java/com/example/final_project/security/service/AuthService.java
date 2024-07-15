package com.example.final_project.security.service;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.requestDto.UserRegisterRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.exceptions.AuthorizationException;
import com.example.final_project.security.jwt.JwtAuthentication;
import com.example.final_project.security.jwt.JwtProvider;
import com.example.final_project.security.jwt.JwtResponse;
import com.example.final_project.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication-related operations.
 * <p>
 * This service provides methods for user login, access token generation, refresh token generation,
 * and obtaining authentication information from the security context.
 * </p>
 *
 * @author A-R
 * @version 1.0
 * @Service - Indicates that an annotated class is a service component.
 * @RequiredArgsConstructor - Lombok annotation to generate a constructor for all final fields,
 * with parameter order same as field order.
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    /**
     * The user service for fetching user data.
     */
    private final UserService usersService;

    /**
     * The JWT provider for generating and validating JWT tokens.
     */
    private final JwtProvider jwtProvider;

    /**
     * Handles user login and returns JWT tokens upon successful authentication.
     *
     * @param userLoginRequestDto the authentication request containing user credentials.
     * @return a JwtResponse containing the generated access and refresh tokens.
     */
    public JwtResponse login(UserLoginRequestDto userLoginRequestDto) {
        logger.info("Starting login process for user with email: {}", userLoginRequestDto.getEmail());

        // Authenticate user and get user details
        final UserResponseDto userResponseDto = usersService.loginUserProfile(userLoginRequestDto);
        logger.info("User authenticated successfully with email: {}", userLoginRequestDto.getEmail());

        // Generate access token
        final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
        logger.info("Generated access token for user with email: {}", userLoginRequestDto.getEmail());

        // Generate refresh token
        final String refreshToken = jwtProvider.generateRefreshToken(userResponseDto);
        logger.info("Generated refresh token for user with email: {}", userLoginRequestDto.getEmail());

        // Set refresh token for the user
        usersService.setRefreshToken(userResponseDto, refreshToken);
        logger.info("Set refresh token for user with email: {}", userLoginRequestDto.getEmail());

        // Return the JwtResponse with the access and refresh tokens
        logger.info("User login and token generation process completed successfully for email: {}", userLoginRequestDto.getEmail());
        return new JwtResponse(accessToken, refreshToken);
    }

    /**
     * Generates a new access token using a valid refresh token.
     * <p>
     * This method first validates the provided refresh token using the {@link JwtProvider}.
     * If the token is valid, it extracts the user login from the token claims,
     * retrieves the stored refresh token for the user, and compares it with the provided token.
     * If they match, it fetches the user data, generates a new access token,
     * and returns a {@link JwtResponse} with the new access token.
     * If any of the validation steps fail, it returns a {@link JwtResponse} with null values.
     * </p>
     *
     * @param refreshToken the refresh token.
     * @return a JwtResponse containing the generated access token, or null values if validation fails.
     */
    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        logger.info("Attempting to get access token using refresh token.");

        // Validate the provided refresh token
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            logInfo("Refresh token is valid.");

            // Extract claims from the refresh token
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);

            // Get the user email from the token claims
            final String userEmail = claims.getSubject();
            logInfo("Extracted user email from refresh token: {}", userEmail);

            // Retrieve the stored refresh token for the user
            UserResponseDto userResponseDto = usersService.getByEmail(userEmail);
            final String savedRefreshToken = userResponseDto.getRefreshToken();
            logInfo("Retrieved stored refresh token for user with email: {}", userEmail);

            // Compare the stored refresh token with the provided token
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                logInfo("Provided refresh token matches the stored refresh token.");

                // Generate a new access token
                final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
                logger.info("Generated new access token for user with email: {}", userEmail);

                // Return a JwtResponse with the new access token
                return new JwtResponse(accessToken, null);
            } else {
                logWarning("Provided refresh token does not match the stored refresh token.");
            }
        } else {
            logWarning("Invalid refresh token provided.");
        }

        // Throw an AuthException if validation fails
        logError("Authorization failed due to invalid refresh token.");
        throw new AuthorizationException("Invalid refreshToken");
    }

    /**
     * Refreshes both access and refresh tokens using a valid refresh token.
     * <p>
     * This method first validates the provided refresh token using the {@link JwtProvider}.
     * If the token is valid, it extracts the user login from the token claims,
     * retrieves the stored refresh token for the user, and compares it with the provided token.
     * If they match, it fetches the user data, generates new access and refresh tokens,
     * updates the stored refresh token for the user, and returns a {@link JwtResponse} with the new tokens.
     * If any of the validation steps fail, it throws an {@link AuthException} with a message indicating
     * an invalid JWT token.
     * </p>
     *
     * @param refreshToken the refresh token.
     * @return a JwtResponse containing the generated access and refresh tokens.
     * @throws AuthException if the refresh token is invalid or the user is not found.
     */
    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        logInfo("Attempting to refresh tokens using the provided refresh token.");

        // Validate the provided refresh token
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            logInfo("Refresh token is valid!.");

            // Extract claims from the refresh token
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);

            // Get the user login from the token claims
            final String userEmail = claims.getSubject();
            logInfo("Extracted user email from refresh token: {}", userEmail);

            // Retrieve the stored refresh token for the user
            UserResponseDto userResponseDto = usersService.getByEmail(userEmail);
            final String savedRefreshToken = userResponseDto.getRefreshToken();
            logInfo("Retrieved stored refresh token for user with email: {}", userEmail);

            // Compare the stored refresh token with the provided token
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                logInfo("Provided refresh token matches the stored refresh token.");

                // Generate new access and refresh tokens
                final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
                final String newRefreshToken = jwtProvider.generateRefreshToken(userResponseDto);
                logInfo("Generated new access token and refresh token for user with email: {}", userEmail);

                // Update the stored refresh token for the user
                usersService.setRefreshToken(userResponseDto, newRefreshToken);
                logInfo("Updated the stored refresh token for user with email: {}", userEmail);

                // Return a JwtResponse with the new access and refresh tokens
                return new JwtResponse(accessToken, newRefreshToken);
            } else {
                logWarning("Provided refresh token does not match the stored refresh token.");
            }
        } else {
            logWarning("Invalid refresh token provided.");
        }

        // Throw an AuthException if validation fails
        logError("Authorization failed due to invalid refresh token.");
        throw new AuthException("Invalid JWT token");
    }

    /**
     * Retrieves the authentication information from the security context.
     *
     * @return the JwtAuthentication object containing the authentication information.
     */
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public JwtResponse createUser(UserRegisterRequestDto userCredentialsDto) throws AuthorizationException {
        logInfo("Starting user registration process for email: {}", userCredentialsDto.getEmail());

        // Register the user profile
        UserResponseDto userResponseDto = usersService.registerUserProfile(userCredentialsDto);
        logInfo("User registered successfully with email: {}", userCredentialsDto.getEmail());

        // Generate access token
        final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
        logInfo("Generated access token for user with email: {}", userCredentialsDto.getEmail());

        // Generate refresh token
        final String refreshToken = jwtProvider.generateRefreshToken(userResponseDto);
        logInfo("Generated refresh token for user with email: {}", userCredentialsDto.getEmail());

        // Set refresh token for the user
        usersService.setRefreshToken(userResponseDto, refreshToken);
        logInfo("Set refresh token for user with email: {}", userCredentialsDto.getEmail());

        // Return the JwtResponse with the access and refresh tokens
        logInfo("User registration and token generation process completed successfully for email: {}", userCredentialsDto.getEmail());
        return new JwtResponse(accessToken, refreshToken);
    }

    private void logInfo(String message, Object... args) {
        logger.info(message, args);
    }

    private void logWarning(String message, Object... args) {
        logger.warn(message, args);
    }

    private void logError(String message, Object... args) {
        logger.error(message, args);
    }
}
