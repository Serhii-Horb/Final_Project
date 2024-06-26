package com.example.final_project.service;

import com.example.final_project.config.MapperUtil;
import com.example.final_project.dto.requestDto.CategoryRequestDto;
import com.example.final_project.dto.responsedDto.CategoryResponseDto;
import com.example.final_project.entity.Category;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    public List<CategoryResponseDto> getCategory() {
        List<Category> categoriesList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryDtoList = MapperUtil.convertList(categoriesList, mappers::convertToCategoryResponseDto);
        return categoryDtoList;
    }


    public CategoryResponseDto getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        CategoryResponseDto categoryDto = null;
        if (categoryOptional.isPresent()) {
            categoryDto = categoryOptional.map(mappers::convertToCategoryResponseDto).orElse(null);
        }
        return categoryDto;
    }

    public void deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
        }
    }

    public CategoryResponseDto insertCategory(CategoryRequestDto categoryDto) {

        Category newCategory = mappers.convertToCategory(categoryDto);
        newCategory.setCategoryId(0L);
        Category savedCategory = categoryRepository.save(newCategory);
        return mappers.convertToCategoryResponseDto(savedCategory);
    }

    public CategoryResponseDto updateCategory(CategoryResponseDto categoryDto) {
        if (categoryDto.getCategoryId() <= 0) {
            return null;
        }

        Optional<Category> categoriesOptional = categoryRepository.findById(categoryDto.getCategoryId());
        if (!categoriesOptional.isPresent()) {
            return null;
        }

        Category category = categoriesOptional.get();
        category.setName(categoryDto.getName());
        Category savedCategory = categoryRepository.save(category);

        return mappers.convertToCategoryResponseDto(savedCategory);
    }
}
