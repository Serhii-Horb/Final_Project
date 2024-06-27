package com.example.final_project.service;

import com.example.final_project.entity.Cart;
import com.example.final_project.entity.User;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CartRepository;
import com.example.final_project.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository usersRepository;
    private final Mappers mappers;
//
//    @ResponseStatus(HttpStatus.CREATED)
//    public CartDto insertCart(@Valid @RequestBody CartDto cartDto) {
//        if (cartDto.getUser() == null || cartDto.getUser().getUserId() == null) {
//            throw new IllegalArgumentException("User ID must be provided in CartDto");
//        }
//
//        Long userId = cartDto.getUser().getUserId();
//        User user = usersRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
//
//        Cart cart = mappers.convertToCart(cartDto);
//        cart.setUser(user);
//
//        try {
//            Cart savedCart = cartRepository.save(cart);
//            return mappers.convertToCartDto(savedCart);
//        } catch (DataAccessException ex) {
//            throw new RuntimeException("Failed to save cart: " + ex.getMessage(), ex);
//        }
//    }
}
