package com.example.final_project.service;

import com.example.final_project.config.MapperUtil;
import com.example.final_project.dto.CategoryDto;
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

    public List<CategoryDto> getCategory() {
        List<Category> categoriesList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = MapperUtil.convertList(categoriesList, mappers::convertToCategoryDto);
        return categoryDtoList;
    }


    public CategoryDto getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        CategoryDto categoryDto = null;
        if (categoryOptional.isPresent()) {
            categoryDto = categoryOptional.map(mappers::convertToCategoryDto).orElse(null);
        }
        return categoryDto;
    }

    public void deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
        }
    }

    public CategoryDto insertCategory(CategoryDto categoryDto) {

        Category newCategory = mappers.convertToCategory(categoryDto);
        newCategory.setCategoryId(0);
        Category savedCategory = categoryRepository.save(newCategory);
        return mappers.convertToCategoryDto(savedCategory);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) {
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

        return mappers.convertToCategoryDto(savedCategory);
    }
}
