package com.example.final_project.service;

import com.example.final_project.dto.requestDto.CategoryRequestDto;
import com.example.final_project.dto.responsedDto.CategoryResponseDto;
import com.example.final_project.entity.Category;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CategoryRepository;
import com.example.final_project.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {
//
//    @InjectMocks
//    private CategoryService categoryService;
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @Mock
//    private Mappers mappers;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void insertCategory() {
//        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
//        categoryRequestDto.setName("Test Category");
//
//        Category category = new Category();
//        category.setCategoryId(2L);
//        category.setName("Test Category");
//
//        Category savedCategory = new Category();
//        savedCategory.setCategoryId(2L);
//        savedCategory.setName("Test Category");
//
//        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
//        categoryResponseDto.setCategoryId(2L);
//        categoryResponseDto.setName("Test Category");
//
//        when(mappers.convertToCategory(any(CategoryRequestDto.class))).thenReturn(category);
//        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);
//        when(mappers.convertToCategoryResponseDto(any(Category.class))).thenReturn(categoryResponseDto);
//
//        CategoryResponseDto result = categoryService.insertCategory(categoryRequestDto);
//
//        assertNotNull(result);
//        assertEquals(2L, result.getCategoryId());
//        assertEquals("Test Category", result.getName());
//    }
}





