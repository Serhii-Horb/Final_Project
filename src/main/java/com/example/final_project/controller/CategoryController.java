package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.CategoryRequestDto;
import com.example.final_project.dto.responsedDto.CategoryResponseDto;
import com.example.final_project.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
@Tag(name = "Category controller.", description = "\"All manipulations with catigories data are carried out here\"")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "shows all categories.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getCategory() {
        return categoryService.getCategory();
    }

    @Operation(summary = "shows the category by Id.")
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "deletes a category by Id.")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

    @Operation(summary = "creates a category.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto insertCategory(@RequestBody CategoryRequestDto categoryDto) {
        return categoryService.insertCategory(categoryDto);
    }

    @Operation(summary = "updates a category by Id.")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto updateCategory(@RequestBody CategoryResponseDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }
}