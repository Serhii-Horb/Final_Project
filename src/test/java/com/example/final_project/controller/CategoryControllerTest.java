package com.example.final_project.controller;

import com.example.final_project.FinalProjectApplication;
import com.example.final_project.dto.requestDto.CategoryRequestDto;
import com.example.final_project.dto.responsedDto.CategoryResponseDto;
import com.example.final_project.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FinalProjectApplication.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryResponseDto categoryResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryId(1L);
        categoryResponseDto.setName("Test Category");
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMINISTRATOR"})
    void testGetCategory() throws Exception {
        List<CategoryResponseDto> categories = Arrays.asList(categoryResponseDto);
        when(categoryService.getCategory()).thenReturn(categories);

        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryId", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test Category")))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER", "ADMINISTRATOR"})
    void testGetCategoryById() throws Exception {
        when(categoryService.getCategoryById(anyLong())).thenReturn(categoryResponseDto);

        mockMvc.perform(get("/categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId", is(1)))
                .andExpect(jsonPath("$.name", is("Test Category")))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testDeleteCategoryById() throws Exception {
        mockMvc.perform(delete("/categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testInsertCategory() throws Exception {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("New Category");

        when(categoryService.insertCategory(any(CategoryRequestDto.class))).thenReturn(categoryResponseDto);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId", is(1)))
                .andExpect(jsonPath("$.name", is("Test Category")))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testUpdateCategory() throws Exception {
        when(categoryService.updateCategory(any(CategoryResponseDto.class))).thenReturn(categoryResponseDto);

        mockMvc.perform(put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryResponseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId", is(1)))
                .andExpect(jsonPath("$.name", is("Test Category")))
                .andDo(print());
    }
}
