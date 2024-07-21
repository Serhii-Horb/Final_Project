package com.example.final_project.service;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.requestDto.UserRegisterRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.Cart;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Role;
import com.example.final_project.exceptions.AuthorizationException;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRegisterRequestDto userRegisterRequestDto;
    private UserLoginRequestDto userLoginRequestDto;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        userService.setAdminEmails("test@example.com");
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("passwordHash");

        userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setName("TestUser");
        userRegisterRequestDto.setEmail("test@example.com");
        userRegisterRequestDto.setPhoneNumber("123456789");
        userRegisterRequestDto.setPasswordHash("testPassword");

        userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setEmail("test@example.com");
        userLoginRequestDto.setPassword("password");

        userResponseDto = new UserResponseDto();
        userResponseDto.setUserID(1L);
        userResponseDto.setName("TestUser");
        userResponseDto.setEmail("test@example.com");
        userResponseDto.setPhoneNumber("123456789");

        Cart testCart = new Cart();
        testCart.setUser(testUser);
    }

    @Test
    void testRegisterUserProfile_UserAlreadyExists() {
        when(userRepositoryMock.getByEmail(userRegisterRequestDto.getEmail())).thenReturn(Optional.of(testUser));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> userService.registerUserProfile(userRegisterRequestDto));
        assertEquals("User already exists.", exception.getMessage());

        verify(userRepositoryMock, times(1)).getByEmail(userRegisterRequestDto.getEmail());
        verify(userRepositoryMock, times(0)).save(any(User.class));
        verify(passwordEncoderMock, times(0)).encode(anyString());
        verify(mappersMock, times(0)).convertRegisterDTOToUser(any(UserRegisterRequestDto.class));
    }

    @Test
    void testRegisterUserProfile_Success() {
        when(userRepositoryMock.getByEmail(userRegisterRequestDto.getEmail())).thenReturn(Optional.empty());
        when(mappersMock.convertRegisterDTOToUser(userRegisterRequestDto)).thenReturn(testUser);
        when(passwordEncoderMock.encode(userRegisterRequestDto.getPasswordHash())).thenReturn("encodedPassword");
        when(userRepositoryMock.save(any(User.class))).thenReturn(testUser);
        when(mappersMock.convertToUserResponseDto(testUser)).thenReturn(userResponseDto);

        UserResponseDto response = userService.registerUserProfile(userRegisterRequestDto);

        assertNotNull(response);
        assertEquals(userResponseDto, response);

        verify(userRepositoryMock, times(1)).getByEmail(userRegisterRequestDto.getEmail());
        verify(mappersMock, times(1)).convertRegisterDTOToUser(userRegisterRequestDto);
        verify(passwordEncoderMock, times(1)).encode(userRegisterRequestDto.getPasswordHash());
        verify(userRepositoryMock, times(1)).save(any(User.class));
        verify(mappersMock, times(1)).convertToUserResponseDto(any(User.class));
    }
    @Test
    void testLoginUserProfile_UserNotFound() {
        when(userRepositoryMock.getByEmail(userLoginRequestDto.getEmail())).thenReturn(Optional.empty());

        AuthorizationException exception = assertThrows(AuthorizationException.class,
                () -> userService.loginUserProfile(userLoginRequestDto));
        assertEquals("User not found.", exception.getMessage());

        verify(userRepositoryMock, times(1)).getByEmail(userLoginRequestDto.getEmail());
        verify(passwordEncoderMock, times(0)).matches(anyString(), anyString());
        verify(mappersMock, times(0)).convertToUserResponseDto(any(User.class));
    }

    @Test
    void testLoginUserProfile_IncorrectPassword() {
        when(userRepositoryMock.getByEmail(userLoginRequestDto.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoderMock.matches(userLoginRequestDto.getPassword(), testUser.getPasswordHash())).thenReturn(false);

        AuthorizationException exception = assertThrows(AuthorizationException.class,
                () -> userService.loginUserProfile(userLoginRequestDto));
        assertEquals("Incorrect password. Please try again.", exception.getMessage());

        verify(userRepositoryMock, times(1)).getByEmail(userLoginRequestDto.getEmail());
        verify(passwordEncoderMock, times(1)).matches(userLoginRequestDto.getPassword(), testUser.getPasswordHash());
        verify(mappersMock, times(0)).convertToUserResponseDto(any(User.class));
    }

    @Test
    void testLoginUserProfile_Success() {
        when(userRepositoryMock.getByEmail(userLoginRequestDto.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoderMock.matches(userLoginRequestDto.getPassword(), testUser.getPasswordHash())).thenReturn(true);
        when(mappersMock.convertToUserResponseDto(testUser)).thenReturn(userResponseDto);

        UserResponseDto response = userService.loginUserProfile(userLoginRequestDto);

        assertNotNull(response);
        assertEquals(userResponseDto, response);

        verify(userRepositoryMock, times(1)).getByEmail(userLoginRequestDto.getEmail());
        verify(passwordEncoderMock, times(1)).matches(userLoginRequestDto.getPassword(), testUser.getPasswordHash());
        verify(mappersMock, times(1)).convertToUserResponseDto(any(User.class));
    }

    @Test
    void testGetByEmail_UserNotFound() {
        when(userRepositoryMock.getByEmail("test@example.com")).thenReturn(Optional.empty());

        AuthorizationException exception = assertThrows(AuthorizationException.class,
                () -> userService.getByEmail("test@example.com"));
        assertEquals("User not found.", exception.getMessage());

        verify(userRepositoryMock, times(1)).getByEmail("test@example.com");
        verify(mappersMock, times(0)).convertToUserResponseDto(any(User.class));
    }

    @Test
    void testGetByEmail_Success() {
        when(userRepositoryMock.getByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(mappersMock.convertToUserResponseDto(testUser)).thenReturn(userResponseDto);

        UserResponseDto response = userService.getByEmail("test@example.com");

        assertNotNull(response);
        assertEquals(userResponseDto, response);

        verify(userRepositoryMock, times(1)).getByEmail("test@example.com");
        verify(mappersMock, times(1)).convertToUserResponseDto(any(User.class));
    }
}