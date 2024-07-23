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

    /**
     * Adds or updates a product in the user's cart.
     *
     * @param cartItemRequestDto DTO containing product ID and quantity.
     * @param userId             ID of the user whose cart is being updated.
     * @return CartItemResponseDto with details of the added or updated cart item.
     */
    public CartItemResponseDto addOrUpdateProductInCart(CartItemRequestDto cartItemRequestDto, Long userId) {
        logInfo("Starting addOrUpdateProductInCart for userId: {} with productId: {}", userId,
                cartItemRequestDto.getProductId());

        // Fetch the cart for the given user ID, or throw an exception if not found.
        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(() ->
                logAndThrow("Cart not found for userId: {}", userId));
        logFoundObject(cart, "Found cart");

        // Fetch the product for the given product ID, or throw an exception if not found.
        Product product = productRepository.findById(cartItemRequestDto.getProductId()).orElseThrow(() ->
                logAndThrow("Product not found for productId: {}", cartItemRequestDto.getProductId()));
        logFoundObject(product, "Found product");

        // Find the cart item by cart ID and product ID, or create a new one if not found.
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

        // Set the quantity for the cart item.
        cartItem.setQuantity(cartItemRequestDto.getQuantity());

        // Save the cart item to the repository.
        try {
            cartItemRepository.save(cartItem);
            logInfo("CartItem saved successfully: {}", cartItem);
        } catch (Exception exception) {
            logError("Error saving cartItem", exception);
            throw new BadRequestException("Error saving cartItem");
        }

        // Convert the cart item to a response DTO and return it.
        return mappers.convertToCartItemResponseDto(cartItem);
    }

    /**
     * Deletes a product from the user's cart by user ID and product ID.
     *
     * @param productId ID of the product to be deleted.
     * @param userId    ID of the user whose cart is being updated.
     */
    public void deleteProductInCartByUserIdAndProductId(Long productId, Long userId) {
        logInfo("Starting deleteProductInCartByUserIdAndProductId for userId: {} with productId: {}",
                userId, productId);

        // Fetch the cart for the given user ID, or throw an exception if not found.
        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(() ->
                logAndThrow("Cart not found for userId: {}", userId));
        logFoundObject(cart, "Found cart");

        // Fetch the cart item for the given cart ID and product ID, or throw an exception if not found.
        CartItem cartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId)
                .orElseThrow(() -> logAndThrow("CartItem not found for cartId: {} and productId: {}",
                        cart.getCartId(), productId));

        // Delete the cart item from the repository.
        try {
            cartItemRepository.delete(cartItem);
            logInfo("CartItem deleted successfully: {}", cartItem);
        } catch (Exception exception) {
            logError("Error deleting cartItem", exception);
            throw new BadRequestException("Error deleting cartItem");
        }
    }

    /**
     * Logs an informational message.
     *
     * @param message The message to log.
     * @param args    Arguments for the message.
     */
    private void logInfo(String message, Object... args) {
        logger.info(message, args);
    }

    /**
     * Logs an error message.
     *
     * @param message The message to log.
     * @param args    Arguments for the message.
     */
    private void logError(String message, Object... args) {
        logger.error(message, args);
    }

    /**
     * Logs a found object with a message.
     *
     * @param object  The object that was found.
     * @param message The message to log.
     * @param <T>     The type of the object.
     */
    private <T> void logFoundObject(T object, String message) {
        logger.debug("{}: {}", message, object);
    }

    /**
     * Logs an error message and throws a BadRequestException.
     *
     * @param message The error message.
     * @param params  Parameters for the error message.
     * @return A BadRequestException with the formatted message.
     */
    private BadRequestException logAndThrow(String message, Object... params) {
        logger.error(message, params);
        return new BadRequestException(String.format(message.replace("{}", "%s"), params));
    }
}
