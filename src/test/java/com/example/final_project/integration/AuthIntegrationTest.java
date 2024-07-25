package com.example.final_project.integration;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.requestDto.UserRegisterRequestDto;
import com.example.final_project.security.jwt.JwtResponse;
import com.example.final_project.security.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class AuthIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void testLogin_Success() throws Exception {
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setEmail("test@example.com");
        userLoginRequestDto.setPassword("password");
        JwtResponse jwtResponse = new JwtResponse("accessToken", "refreshToken");

        when(authService.login(userLoginRequestDto)).thenReturn(jwtResponse);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));

        verify(authService, times(1)).login(userLoginRequestDto);
    }

    @Test
    void testGetNewAccessToken_Success() throws Exception {
        JwtResponse jwtResponse = new JwtResponse("newAccessToken", null);

        when(authService.getAccessToken("refreshToken")).thenReturn(jwtResponse);

        mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"refreshToken\": \"refreshToken\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.refreshToken").isEmpty());

        verify(authService, times(1)).getAccessToken("refreshToken");
    }

    @Test
    void testRegister_Success() throws Exception {
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setName("TestUser");
        userRegisterRequestDto.setEmail("test@example.com");
        userRegisterRequestDto.setPhoneNumber("123456789");
        userRegisterRequestDto.setPasswordHash("testPassword");

        JwtResponse jwtResponse = new JwtResponse("accessToken", "refreshToken");

        when(authService.createUser(any(UserRegisterRequestDto.class))).thenReturn(jwtResponse);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TestUser\", \"email\": \"test@example.com\", " +
                                "\"phoneNumber\": \"123456789\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));

        verify(authService, times(1)).createUser(any(UserRegisterRequestDto.class));
    }
}
