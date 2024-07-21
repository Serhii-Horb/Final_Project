package com.example.final_project.repository;

import com.example.final_project.entity.Cart;
import com.example.final_project.entity.CartItem;
import com.example.final_project.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

    @DataJpaTest
    class CartItemRepositoryTest {

        @Autowired
        private CartItemRepository cartItemRepository;

        @Autowired
        private CartRepository cartRepository;

        @Autowired
        private ProductRepository productRepository;

        private Cart cart;
        private Product product;
        private CartItem cartItem;

        @BeforeEach
        void setUp() {
            cart = new Cart();
            cartRepository.save(cart);

            product = new Product();
            product.setName("Test Product");
            product.setPrice(BigDecimal.valueOf(10.00));
            productRepository.save(product);

            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(5);
            cartItemRepository.save(cartItem);
        }

        @Test
        void testFindByCart_CartIdAndProduct_ProductId_CartItemExists() {
            Optional<CartItem> foundCartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(),
                    product.getProductId());

            assertTrue(foundCartItem.isPresent(), "CartItem should be found for existing cartId and productId.");
            assertEquals(cartItem.getCartItemId(), foundCartItem.get().getCartItemId(), "Found CartItem should " +
                    "have the same ID as the saved CartItem.");
        }

        @Test
        void testFindByCart_CartIdAndProduct_ProductId_CartItemDoesNotExist() {
            Optional<CartItem> foundCartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(999L,
                    999L);

            assertFalse(foundCartItem.isPresent(), "CartItem should not be found for non-existent cartId " +
                    "and productId.");
        }
    }