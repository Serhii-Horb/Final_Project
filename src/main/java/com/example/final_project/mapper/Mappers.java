package com.example.final_project.mapper;

import com.example.final_project.dto.*;
import com.example.final_project.entity.Cart;
import com.example.final_project.entity.Category;
import com.example.final_project.entity.Product;
import com.example.final_project.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;

    public UserDto convertToUserDto(User user) {
        UserDto usersDto = modelMapper.map(user, UserDto.class);
        usersDto.setPasswordHash("*******");
        return usersDto;
    }

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

    public User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public CartDto convertToCartDto(Cart cart) {
        return modelMapper.map(cart, CartDto.class);
    }

    public Cart convertToCart(CartDto cartDto) {
        return modelMapper.map(cartDto, Cart.class);
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
