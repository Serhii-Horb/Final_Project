package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.CategoryRequestDto;
import com.example.final_project.dto.responsedDto.CategoryResponseDto;
import com.example.final_project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getCategory() {
        return categoryService.getCategory();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto insertCategory(@RequestBody CategoryRequestDto categoryDto) {
        return categoryService.insertCategory(categoryDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto updateCategory(@RequestBody CategoryResponseDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }
}