package com.example.final_project.service;

import com.example.final_project.config.MapperUtil;
import com.example.final_project.entity.CartItem;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CartItemRepository;
import com.example.final_project.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final Mappers mappers;
    private final CartRepository cartRepository;

//    public List<CartItemDto> getCartItem() {
//        List<CartItem> cartItemList = cartItemRepository.findAll();
//        List<CartItemDto> cartItemDtoList = MapperUtil.convertList(cartItemList, mappers::convertToCartItemDto);
//        return cartItemDtoList;
//    }
//
//    public CartItemDto getCartItemById(Long id) {
//        Optional<CartItem> cartItem = cartItemRepository.findById(id);
//        CartItemDto cartItemDto = null;
//        if (cartItem.isPresent()) {
//            cartItemDto = mappers.convertToCartItemDto(cartItem.get());
//        }
//        return cartItemDto;
//    }
//
//    public CartItemDto insertCartItem(CartItemDto cartItemDto) {
//        CartItem cartItem = mappers.convertToCartItem(cartItemDto);
//        cartItem.setCartItemId(null);
//        CartItem newCartItem = cartItemRepository.save(cartItem);
//        return mappers.convertToCartItemDto(newCartItem);
//    }
//
//    public CartItemDto updateCartItem(CartItemDto cartItemDto) {
//        if (cartItemDto.getCartItemId() <= 0) {
//            return null;
//        }
//        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemDto.getCartItemId());
//        if (cartItemOptional.isEmpty()) {
//            return null;
//        }
//        CartItem cartItem = mappers.convertToCartItem(cartItemDto);
//        CartItem newCartItem = cartItemRepository.save(cartItem);
//        return mappers.convertToCartItemDto(newCartItem);
//    }
//
//    public void deleteCartItemById(Long id) {
//        Optional<CartItem> cartItem = cartItemRepository.findById(id);
//        cartItem.ifPresent(cartItemRepository::delete);
//    }
}
