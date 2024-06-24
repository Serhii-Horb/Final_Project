package com.example.final_project.mapper;

import com.example.final_project.dto.CartDto;
import com.example.final_project.dto.CartItemDto;
import com.example.final_project.entity.CartItem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;

    public CartItem convertToCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = modelMapper.map(cartItemDto, CartItem.class);
        return cartItem;
    }


    public CartItemDto convertToCartItemDto(CartItem cartItem) {
        CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
        return cartItemDto;
    }
}
