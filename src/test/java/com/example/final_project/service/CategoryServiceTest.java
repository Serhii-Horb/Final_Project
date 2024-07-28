package com.example.final_project.service;

import com.example.final_project.dto.requestDto.CategoryRequestDto;
import com.example.final_project.dto.responsedDto.CategoryResponseDto;
import com.example.final_project.entity.Category;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Mappers mappers;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryRequestDto categoryRequestDto;
    private CategoryResponseDto categoryResponseDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCategoryId(1L);
        category.setName("Test Category");

        categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("Test Category");

        categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryId(1L);
        categoryResponseDto.setName("Test Category");
    }

    @Test
    void testGetCategory() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        List<CategoryResponseDto> categories = categoryService.getCategory();

        assertNotNull(categories);
    }

    @Test
    void testGetCategoryById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(mappers.convertToCategoryResponseDto(any(Category.class))).thenReturn(categoryResponseDto);

        CategoryResponseDto result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals("Test Category", result.getName());
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundInDbException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    void testDeleteCategoryById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        categoryService.deleteCategoryById(1L);

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @Test
    void testDeleteCategoryById_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundInDbException.class, () -> categoryService.deleteCategoryById(1L));
    }

    @Test
    void testInsertCategory() {
        when(mappers.convertToCategory(any(CategoryRequestDto.class))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(mappers.convertToCategoryResponseDto(any(Category.class))).thenReturn(categoryResponseDto);

        CategoryResponseDto result = categoryService.insertCategory(categoryRequestDto);

        assertNotNull(result);
        assertEquals("Test Category", result.getName());
    }

    @Test
    void testInsertCategory_NullOrEmptyName() {
        CategoryRequestDto invalidDto = new CategoryRequestDto();
        invalidDto.setName("");

        assertThrows(BadRequestException.class, () -> categoryService.insertCategory(invalidDto));
    }

    @Test
    void testUpdateCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(mappers.convertToCategoryResponseDto(any(Category.class))).thenReturn(categoryResponseDto);

        CategoryResponseDto result = categoryService.updateCategory(categoryResponseDto);

        assertNotNull(result);
        assertEquals("Test Category", result.getName());
    }

    @Test
    void testUpdateCategory_InvalidId() {
        CategoryResponseDto invalidDto = new CategoryResponseDto();
        invalidDto.setCategoryId(-1L);

        assertNull(categoryService.updateCategory(invalidDto));
    }

    @Test
    void testUpdateCategory_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundInDbException.class, () -> categoryService.updateCategory(categoryResponseDto));
    }
}







