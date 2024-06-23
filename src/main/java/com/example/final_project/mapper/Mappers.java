package com.example.final_project.mapper;

import com.example.final_project.dto.CategoryDto;
import com.example.final_project.dto.ProductRequestDto;
import com.example.final_project.dto.ProductResponseDto;
import com.example.final_project.entity.Category;
import com.example.final_project.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mappers {
    @Autowired
    private ModelMapper modelMapper;

    public CategoryDto convertToCategoryDto(Category category) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    public Category convertToCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        return category;
    }

    public ProductResponseDto convertToProductDto(Product product) {
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        return productResponseDto;
    }

    public Product convertToProduct(ProductResponseDto productResponseDto) {
        Product product = modelMapper.map(productResponseDto, Product.class);
        return product;
    }
    public ProductRequestDto convertToProductRequestDto(Product product) {
        ProductRequestDto productRequestDto = modelMapper.map(product, ProductRequestDto.class);
        return productRequestDto;
    }

    public Product convertToProduct(ProductRequestDto productRequestDto) {
        Product product = modelMapper.map(productRequestDto, Product.class);
        return product;
    }
}
