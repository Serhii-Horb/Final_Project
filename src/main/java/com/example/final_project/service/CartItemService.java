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
        logInfo("Starting addOrUpdateProductInCart for userId: {} with productId: {}", userId,
                cartItemRequestDto.getProductId());
        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(() ->
                logAndThrow("Cart not found for userId: {}", userId));
        logFoundObject(cart, "Found cart");

        Product product = productRepository.findById(cartItemRequestDto.getProductId()).orElseThrow(() ->
                logAndThrow("Product not found for productId: {}", cartItemRequestDto.getProductId()));
        logFoundObject(product, "Found product");

        CartItem cartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(),
                product.getProductId()).orElse(null);

        if (cartItem == null) {
            logInfo("CartItem not found, creating new one.");
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
        } else {
            logInfo("CartItem found, updating quantity.");
        }

        cartItem.setQuantity(cartItemRequestDto.getQuantity());

        try {
            cartItemRepository.save(cartItem);
            logInfo("CartItem saved successfully: {}", cartItem);
        } catch (Exception exception) {
            logError("Error saving cartItem", exception);
            throw new BadRequestException("Error saving cartItem");
        }

        return mappers.convertToCartItemResponseDto(cartItem);
    }

    public void deleteProductInCartByUserIdAndProductId(Long productId, Long userId) {
        logInfo("Starting deleteProductInCartByUserIdAndProductId for userId: {} with productId: {}",
                userId, productId);
        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(() ->
                logAndThrow("Cart not found for userId: {}", userId));
        logFoundObject(cart, "Found cart");

        CartItem cartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId)
                .orElseThrow(() -> logAndThrow("CartItem not found for cartId: {} and productId: {}",
                        cart.getCartId(), productId));
        try {
            cartItemRepository.delete(cartItem);
            logInfo("CartItem deleted successfully: {}", cartItem);
        } catch (Exception exception) {
            logError("Error deleting cartItem", exception);
            throw new BadRequestException("Error deleting cartItem");
        }
    }

    private void logInfo(String message, Object... args) {
        logger.info(message, args);
    }

    private void logError(String message, Object... args) {
        logger.error(message, args);
    }

    private <T> void logFoundObject(T object, String message) {
        logger.debug("{}: {}", message, object);
    }

    private BadRequestException logAndThrow(String message, Object... params) {
        logger.error(message, params);
        return new BadRequestException(String.format(message.replace("{}", "%s"), params));
    }
}
