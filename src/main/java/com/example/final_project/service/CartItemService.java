package com.example.final_project.service;

import com.example.final_project.dto.requestDto.CartItemRequestDto;
import com.example.final_project.dto.responsedDto.CartItemResponseDto;
import com.example.final_project.entity.Cart;
import com.example.final_project.entity.CartItem;
import com.example.final_project.entity.Product;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CartItemRepository;
import com.example.final_project.repository.CartRepository;
import com.example.final_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private static final Logger logger = LoggerFactory.getLogger(CartItemService.class);

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    private final Mappers mappers;

    public CartItemResponseDto addOrUpdateProductInCart(CartItemRequestDto cartItemRequestDto, Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(() -> new BadRequestException("Cart not found."));
        Product product = productRepository.findById(cartItemRequestDto.getProductId()).orElseThrow(
                () -> new BadRequestException("Product not found."));
        CartItem cartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(),
                product.getProductId()).orElse(null);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
        }
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        try {
            cartItemRepository.save(cartItem);
        } catch (Exception exception) {
            logger.error("Error saving cartItem", exception);
            throw new BadRequestException("Error saving cartItem");
        }
        return mappers.convertToCartItemResponseDto(cartItem);
    }

    public void deleteProductInCartByUserIdAndProductId(Long productId, Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(() -> new BadRequestException("Cart not found."));
        CartItem cartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(),
                productId).orElseThrow(() -> new BadRequestException("CartItem not found."));
        cartItemRepository.delete(cartItem);
    }
}
