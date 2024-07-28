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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    public List<CategoryResponseDto> getCategory() {
        logger.info("Fetching all categories");
        List<Category> categoriesList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryDtoList = MapperUtil.convertList(categoriesList, mappers::convertToCategoryResponseDto);
        logger.info("Fetched {} categories", categoriesList.size());
        return categoryDtoList;
    }

    public CategoryResponseDto getCategoryById(Long id) {
        logger.info("Fetching category with ID: {}", id);
        return mappers.convertToCategoryResponseDto(categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundInDbException("Incorrect id of category.")));
    }

    public void deleteCategoryById(Long id) {
        logger.info("Deleting category with ID: {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id of product."));
        categoryRepository.delete(category);
        logger.info("Deleted category with ID: {}", id);
    }

    public CategoryResponseDto insertCategory(CategoryRequestDto categoryDto) {
        if (categoryDto == null || categoryDto.getName() == null || categoryDto.getName().isEmpty()) {
            logger.error("Category name cannot be null or empty");
            throw new BadRequestException("Category name cannot be null or empty");
        }
        try {
            logger.info("Inserting new category with name: {}", categoryDto.getName());
            Category newCategory = mappers.convertToCategory(categoryDto);
            Category savedCategory = categoryRepository.save(newCategory);
            logger.info("Inserted category with ID: {}", savedCategory.getCategoryId());
            return mappers.convertToCategoryResponseDto(savedCategory);
        } catch (Exception e) {
            logger.error("Failed to insert category: {}", e.getMessage());
            throw new BadRequestException("Failed to insert category: " + e.getMessage());
        }
    }

    public CategoryResponseDto updateCategory(CategoryResponseDto categoryDto) {
        if (categoryDto.getCategoryId() <= 0) {
            logger.error("Invalid category ID: {}", categoryDto.getCategoryId());
            return null;
        }

        logger.info("Updating category with ID: {}", categoryDto.getCategoryId());
        Optional<Category> categoriesOptional = categoryRepository.findById(categoryDto.getCategoryId());
        if (!categoriesOptional.isPresent()) {
            logger.error("Category ID not found: {}", categoryDto.getCategoryId());
            throw new NotFoundInDbException("Id Not Found");
        }

        try {
            Category category = categoriesOptional.get();
            category.setName(categoryDto.getName());
            Category savedCategory = categoryRepository.save(category);
            logger.info("Updated category with ID: {}", savedCategory.getCategoryId());
            return mappers.convertToCategoryResponseDto(savedCategory);
        } catch (Exception e) {
            logger.error("Failed to update category: {}", e.getMessage());
            throw new BadRequestException("Bad Request");
        }
    }
}
