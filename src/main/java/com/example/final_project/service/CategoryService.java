package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.CategoryRequestDto;
import com.example.final_project.dto.responsedDto.CategoryResponseDto;
import com.example.final_project.entity.Category;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.exceptions.NotFoundInDbException;
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
        return mappers.convertToCategoryResponseDto(categoryRepository.findById(id).orElseThrow(()
                -> new NotFoundInDbException("Incorrect id of categoty.")));
    }

    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id of product."));
        categoryRepository.delete(category);
    }

public CategoryResponseDto insertCategory(CategoryRequestDto categoryDto) {
    if (categoryDto == null || categoryDto.getName() == null || categoryDto.getName().isEmpty()) {
        throw new BadRequestException("Category name cannot be null or empty");
    }
    try {
        Category newCategory = mappers.convertToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(newCategory);
        return mappers.convertToCategoryResponseDto(savedCategory);
    } catch (Exception e) {
        throw new BadRequestException("Failed to insert category: " + e.getMessage());
    }
}


    public CategoryResponseDto updateCategory(CategoryResponseDto categoryDto) {
        if (categoryDto.getCategoryId() <= 0) {
            return null;
        }

        Optional<Category> categoriesOptional = categoryRepository.findById(categoryDto.getCategoryId());
        if (!categoriesOptional.isPresent()) {
            throw new NotFoundInDbException("Id Not Found");
        }

        try {
            Category category = categoriesOptional.get();
            category.setName(categoryDto.getName());
            Category savedCategory = categoryRepository.save(category);
            return mappers.convertToCategoryResponseDto(savedCategory);
        } catch (Exception e) {
            throw new BadRequestException("Bad Request");
        }
    }
}
