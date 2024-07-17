package com.example.final_project.controller;

import com.example.final_project.dto.ProductCountDto;
import com.example.final_project.dto.requestDto.ProductRequestDto;
import com.example.final_project.dto.responsedDto.ProductResponseDto;
import com.example.final_project.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
@Tag(name = "Product controller", description = "\"All manipulations with products data are carried out here\"")
public class ProductController {
    private final ProductService productsService;

    @Operation(summary = "shows all the products")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getAllProducts() {
        return productsService.getAllProducts();
    }

    @Operation(summary = "shows a product by Id")
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productsService.getProductById(id);
    }

    @Operation(summary = "deletes a product by Id")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long id) {
        productsService.deleteProductById(id);
    }

    @Operation(summary = "creates a product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProduct(@RequestBody ProductRequestDto productsRequestDto) {
        productsService.insertProduct(productsRequestDto);
    }

    @Operation(summary = "updates the product by Id")
    @PutMapping (value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody @Valid ProductRequestDto productsRequestDto, @PathVariable Long id, BindingResult bindingResult) {
        productsService.updateProduct(productsRequestDto, id, bindingResult);
    }


    @Operation(summary = "filter top 10")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/top10")
    public List<ProductCountDto> getTop10Products(@RequestParam(value = "status", required = false) String status) {
        return productsService.getTop10Products(status);
    }
}