package com.example.final_project.mapper;

import com.example.final_project.dto.CartDto;
import com.example.final_project.dto.UserDto;
import com.example.final_project.entity.Cart;
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

    public User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public CartDto convertToCartDto(Cart cart) {
        return modelMapper.map(cart, CartDto.class);
    }

    public Cart convertToCart(CartDto cartDto) {
        return modelMapper.map(cartDto, Cart.class);
    }
}
