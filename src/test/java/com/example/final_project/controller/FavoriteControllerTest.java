package com.example.final_project.controller;

import com.example.final_project.dto.responsedDto.FavoriteResponseDto;
import com.example.final_project.dto.responsedDto.ProductResponseDto;
import com.example.final_project.security.config.SecurityConfig;
import com.example.final_project.security.jwt.JwtProvider;
import com.example.final_project.service.FavoriteService;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest(FavoriteController.class)
@Import(SecurityConfig.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FavoriteService favoriteServiceMock;

    private FavoriteResponseDto favoriteResponseDto;

    @MockBean
    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .productId(1L)
                .name("New Garden Hoe")
                .description("Sturdy garden hoe for weeding")
                .price(new BigDecimal("18.00"))
                .discountPrice(new BigDecimal("17.00"))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .imageURL("http://images/garden_hoe.jpg")
                .build();

        favoriteResponseDto = FavoriteResponseDto.builder()
                .favoriteId(1L)
                .userId(1L)
                .productResponseDto(productResponseDto)
                .build();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testAddFavoriteByUserId() throws Exception {
        Long userId = 1L;
        Long productId = 1L;

        doNothing().when(favoriteServiceMock).addFavoriteByUserId(userId, productId);

        this.mockMvc.perform(post("/favorites/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productId)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testDeleteFavoriteByProductId() throws Exception {
        Long userId = 1L;
        Long productId = 1L;

        doNothing().when(favoriteServiceMock).deleteFavoriteByUserId(userId, productId);

        this.mockMvc.perform(delete("/favorites/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productId)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMINISTRATOR"})
    void testGetAllFavoritesByUserId() throws Exception {
        Long userId = 1L;

        List<FavoriteResponseDto> favoriteResponseDtoList = List.of(favoriteResponseDto);

        when(favoriteServiceMock.getAllFavoritesByUserId(anyLong())).thenReturn(favoriteResponseDtoList);

        this.mockMvc.perform(get("/favorites/{userId}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(favoriteResponseDtoList.size()))
                .andExpect(jsonPath("$[0].userId").value(favoriteResponseDtoList.getFirst().getUserId()))
                .andExpect(jsonPath("$[0].favoriteId").value(favoriteResponseDtoList.getFirst().getFavoriteId()))
                .andExpect(jsonPath("$[0].product.productId").value(favoriteResponseDtoList.getFirst().getProductResponseDto().getProductId()))
                .andExpect(jsonPath("$[0].product.name").value(favoriteResponseDtoList.getFirst().getProductResponseDto().getName()));
    }
}