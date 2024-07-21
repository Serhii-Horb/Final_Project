package com.example.final_project.repository;

import com.example.final_project.entity.Cart;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Cart cart;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("testuser@example.com");
        user.setPasswordHash("testPassword");
        user.setRole(Role.USER);

        userRepository.save(user);

        cart = new Cart();
        cart.setUser(user);

        cartRepository.save(cart);
    }

    @Test
    void testFindByUser_UserId_CartExists() {
        Optional<Cart> foundCart = cartRepository.findByUser_UserId(user.getUserId());

        assertTrue(foundCart.isPresent(), "Cart should be found for existing userId.");
        assertEquals(cart.getCartId(), foundCart.get().getCartId(), "Found cart should have the same ID as the saved cart.");
    }

    @Test
    void testFindByUser_UserId_CartDoesNotExist() {
        Optional<Cart> foundCart = cartRepository.findByUser_UserId(999L); // Using an ID that does not exist

        assertFalse(foundCart.isPresent(), "Cart should not be found for non-existent userId.");
    }
}