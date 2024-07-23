package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.UserUpdateRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.security.config.SecurityConfig;
import com.example.final_project.security.jwt.JwtProvider;
import com.example.final_project.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userServiceMock;

    private UserUpdateRequestDto userUpdateRequestDto;
    private UserResponseDto userResponseDto;

    @MockBean
    private JwtProvider jwtProvider;

    private List<UserResponseDto> userResponseDtoList;

    @BeforeEach
    void setUp() {
        userUpdateRequestDto = new UserUpdateRequestDto();
        userUpdateRequestDto.setName("TestUser");
        userUpdateRequestDto.setPhone("123456789");

        userResponseDto = new UserResponseDto();
        userResponseDto.setUserID(1L);
        userResponseDto.setName("TestUser");
        userResponseDto.setEmail("test@example.com");
        userResponseDto.setPhoneNumber("123456789");

        UserResponseDto user1 = UserResponseDto.builder()
                .userID(1L)
                .name("User1")
                .email("user1@example.com")
                .phoneNumber("123456789")
                .build();

        UserResponseDto user2 = UserResponseDto.builder()
                .userID(2L)
                .name("User2")
                .email("user2@example.com")
                .phoneNumber("987654321")
                .build();

        userResponseDtoList = Arrays.asList(user1, user2);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void updateUserProfile() throws Exception {
        Long userId = 1L;

        when(userServiceMock.updateUserProfile(any(UserUpdateRequestDto.class), eq(userId)))
                .thenReturn(userResponseDto);

        this.mockMvc.perform(put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(userResponseDto.getUserID()))
                .andExpect(jsonPath("$.name").value(userResponseDto.getName()))
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(userResponseDto.getPhoneNumber()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void deleteUserProfileById() throws Exception {
        Long userId = 1L;

        doNothing().when(userServiceMock).deleteUserProfileById(userId);

        this.mockMvc.perform(delete("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void getAllUsersProfiles() throws Exception {
        when(userServiceMock.getAllUsers()).thenReturn(userResponseDtoList);

        this.mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userID").value(userResponseDtoList.get(0).getUserID()))
                .andExpect(jsonPath("$[0].name").value(userResponseDtoList.get(0).getName()))
                .andExpect(jsonPath("$[0].email").value(userResponseDtoList.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phoneNumber").value(userResponseDtoList.get(0).getPhoneNumber()))
                .andExpect(jsonPath("$[1].userID").value(userResponseDtoList.get(1).getUserID()))
                .andExpect(jsonPath("$[1].name").value(userResponseDtoList.get(1).getName()))
                .andExpect(jsonPath("$[1].email").value(userResponseDtoList.get(1).getEmail()))
                .andExpect(jsonPath("$[1].phoneNumber").value(userResponseDtoList.get(1).getPhoneNumber()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void getUserProfileById() throws Exception {
        Long userId = 1L;

        when(userServiceMock.getUserById(userId)).thenReturn(userResponseDto);

        this.mockMvc.perform(get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(userResponseDto.getUserID()))
                .andExpect(jsonPath("$.name").value(userResponseDto.getName()))
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(userResponseDto.getPhoneNumber()));
    }
}