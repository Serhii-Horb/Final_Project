package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.ProductRequestDto;
import com.example.final_project.dto.responsedDto.ProductResponseDto;
import com.example.final_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getAllProducts() {
        return productsService.getAllProducts();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productsService.getProductById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id) {
        productsService.deleteProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProduct(@RequestBody ProductRequestDto productsRequestDto) {
        productsService.insertProduct(productsRequestDto);
    }

    @PutMapping (value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody ProductRequestDto productsRequestDto, @PathVariable Long id) {
        productsService.updateProduct(productsRequestDto, id);
    }
}