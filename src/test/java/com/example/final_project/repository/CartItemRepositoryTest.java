package com.example.final_project.repository;

import com.example.final_project.entity.CartItem;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class CartItemRepositoryTest {
    @Autowired
    private CartItemRepository cartItemTest;

    @Test
    @DisplayName("Test for getting singleton from data base")
    void testGet() {
        CartItem cartItemExpected = new CartItem();
        cartItemExpected.setCartItemId(1L);
        Optional<CartItem> cartItemActual = cartItemTest.findById(1L);
        Assertions.assertTrue(cartItemActual.isPresent());
        Assertions.assertEquals(cartItemExpected.getCartItemId(), cartItemActual.get().getCartItemId());
        System.out.println(cartItemExpected.getCartItemId());
        System.out.println(cartItemActual.get().getCartItemId());
    }

    @Test
    @DisplayName("Test for creating object")
    void testInsert() {
        CartItem cartItemExpected = new CartItem();
        cartItemExpected.setQuantity(17);

        CartItem cartItemActual = cartItemTest.save(cartItemExpected);

        Assertions.assertNotNull(cartItemActual);
        Assertions.assertTrue(cartItemExpected.getCartItemId() > 0);
        Assertions.assertEquals(cartItemExpected.getCartItemId(), cartItemActual.getCartItemId());
    }

    @Test
    @DisplayName("Test for a edition of cartItem")
    void testEdit() {
        Optional<CartItem> cartItemDb = cartItemTest.findById(1L);
        Assertions.assertTrue(cartItemDb.isPresent());
        System.out.println(cartItemDb.get().getCartItemId());

        CartItem cartItemExpected = cartItemDb.get();
        cartItemExpected.setCartItemId(3L);
        System.out.println(cartItemExpected.getCartItemId());

        CartItem cartItemActual = cartItemTest.save(cartItemExpected);
        Assertions.assertNotNull(cartItemActual);
        Assertions.assertEquals(cartItemExpected.getCartItemId(), cartItemActual.getCartItemId());
        System.out.println(cartItemActual.getCartItemId());
    }

    @Test
    @DisplayName("Test of POJO deleting")
    void testDelete() {
        Optional<CartItem> cartItemDelete = cartItemTest.findById(1L);
        Assertions.assertTrue(cartItemDelete.isPresent());

        cartItemTest.delete(cartItemDelete.get());
        Optional<CartItem> cartItemActual = cartItemTest.findById(1L);
        Assertions.assertFalse(cartItemActual.isPresent());
    }
}