package com.example.final_project.controller;

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

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<CategoryDto> getCategory() {
//        return categoryService.getCategory();
//    }
//
//    @GetMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CategoryDto getCategoryById(@PathVariable Long id) {
//        return categoryService.getCategoryById(id);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteCategoryById(@PathVariable Long id) {
//        categoryService.deleteCategoryById(id);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public CategoryDto insertCategory(@RequestBody CategoryDto categoryDto) {
//        return categoryService.insertCategory(categoryDto);
//    }
//
//    @PutMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
//        return categoryService.updateCategory(categoryDto);
//    }
}