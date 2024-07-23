package com.example.final_project.security.service;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.requestDto.UserRegisterRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.security.jwt.JwtAuthentication;
import com.example.final_project.security.jwt.JwtProvider;
import com.example.final_project.security.jwt.JwtResponse;
import com.example.final_project.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.final_project.exceptions.AuthorizationException;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService usersService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    private UserLoginRequestDto userLoginRequestDto;
    private UserRegisterRequestDto userRegisterRequestDto;
    private UserResponseDto userResponseDto;
    private Claims claims;

    @BeforeEach
    public void setUp() {
        userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setName("TestUser");
        userRegisterRequestDto.setEmail("test@example.com");
        userRegisterRequestDto.setPhoneNumber("123456789");
        userRegisterRequestDto.setPasswordHash("testPassword");

        userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setEmail("test@example.com");
        userLoginRequestDto.setPassword("testPassword");


        userResponseDto = new UserResponseDto();
        userResponseDto.setUserID(1L);
        userResponseDto.setName("TestUser");
        userResponseDto.setEmail("test@example.com");
        userResponseDto.setPhoneNumber("123456789");
        userResponseDto.setRefreshToken("refreshToken");

        claims = mock(Claims.class);
    }

    @Test
    void testLogin_Success() {
        when(usersService.loginUserProfile(userLoginRequestDto)).thenReturn(userResponseDto);
        when(jwtProvider.generateAccessToken(userResponseDto)).thenReturn("accessToken");
        when(jwtProvider.generateRefreshToken(userResponseDto)).thenReturn("refreshToken");
        doNothing().when(usersService).setRefreshToken(userResponseDto, "refreshToken");

        JwtResponse response = authService.login(userLoginRequestDto);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(usersService, times(1)).loginUserProfile(userLoginRequestDto);
        verify(jwtProvider, times(1)).generateAccessToken(userResponseDto);
        verify(jwtProvider, times(1)).generateRefreshToken(userResponseDto);
        verify(usersService, times(1)).setRefreshToken(userResponseDto, "refreshToken");
    }

    @Test
    void testGetAccessToken_Success() {
        when(jwtProvider.validateRefreshToken(anyString())).thenReturn(true);
        when(jwtProvider.getRefreshClaims(anyString())).thenReturn(claims);
        when(usersService.getByEmail("test@example.com")).thenReturn(userResponseDto);
        when(jwtProvider.generateAccessToken(userResponseDto)).thenReturn("newAccessToken");
        when(claims.getSubject()).thenReturn("test@example.com");

        JwtResponse response = authService.getAccessToken("refreshToken");

        assertNotNull(response);
        assertEquals("newAccessToken", response.getAccessToken());
        assertNull(response.getRefreshToken());
        verify(jwtProvider, times(1)).validateRefreshToken("refreshToken");
        verify(jwtProvider, times(1)).getRefreshClaims("refreshToken");
        verify(usersService, times(1)).getByEmail("test@example.com");
        verify(jwtProvider, times(1)).generateAccessToken(userResponseDto);
    }

    @Test
    void testGetAccessToken_InvalidRefreshToken() {
        when(jwtProvider.validateRefreshToken(anyString())).thenReturn(false);

        AuthorizationException thrown = assertThrows(AuthorizationException.class,
                () -> authService.getAccessToken("invalidRefreshToken"));
        assertEquals("Invalid refreshToken", thrown.getMessage());
        verify(jwtProvider, times(1)).validateRefreshToken("invalidRefreshToken");
    }

    @Test
    void testRefresh_Success() throws AuthException {
        when(claims.getSubject()).thenReturn("test@example.com");

        when(jwtProvider.validateRefreshToken("refreshToken")).thenReturn(true);
        when(jwtProvider.getRefreshClaims("refreshToken")).thenReturn(claims);
        when(usersService.getByEmail("test@example.com")).thenReturn(userResponseDto);
        when(jwtProvider.generateAccessToken(userResponseDto)).thenReturn("newAccessToken");
        when(jwtProvider.generateRefreshToken(userResponseDto)).thenReturn("newRefreshToken");
        doNothing().when(usersService).setRefreshToken(any(UserResponseDto.class), anyString());

        JwtResponse response = authService.refresh("refreshToken");

        assertNotNull(response);
        assertEquals("newAccessToken", response.getAccessToken());
        assertEquals("newRefreshToken", response.getRefreshToken());
        verify(jwtProvider, times(1)).validateRefreshToken("refreshToken");
        verify(jwtProvider, times(1)).getRefreshClaims("refreshToken");
        verify(usersService, times(1)).getByEmail("test@example.com");
        verify(jwtProvider, times(1)).generateAccessToken(userResponseDto);
        verify(jwtProvider, times(1)).generateRefreshToken(userResponseDto);
        verify(usersService, times(1)).setRefreshToken(userResponseDto, "newRefreshToken");
    }

    @Test
    void testCreateUser_Success() {
        when(usersService.registerUserProfile(userRegisterRequestDto)).thenReturn(userResponseDto);
        when(jwtProvider.generateAccessToken(userResponseDto)).thenReturn("accessToken");
        when(jwtProvider.generateRefreshToken(userResponseDto)).thenReturn("refreshToken");
        doNothing().when(usersService).setRefreshToken(userResponseDto, "refreshToken");

        JwtResponse response = authService.createUser(userRegisterRequestDto);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(usersService, times(1)).registerUserProfile(userRegisterRequestDto);
        verify(jwtProvider, times(1)).generateAccessToken(userResponseDto);
        verify(jwtProvider, times(1)).generateRefreshToken(userResponseDto);
        verify(usersService, times(1)).setRefreshToken(userResponseDto, "refreshToken");
    }

    @Test
    void testGetAuthInfo() {
        JwtAuthentication jwtAuthentication = mock(JwtAuthentication.class);
        SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);

        JwtAuthentication result = authService.getAuthInfo();

        assertNotNull(result);
        assertEquals(jwtAuthentication, result);
    }
}