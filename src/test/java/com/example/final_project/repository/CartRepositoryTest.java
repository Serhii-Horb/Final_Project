//package com.example.final_project.repository;
//
//import com.example.final_project.entity.Cart;
//import com.example.final_project.entity.User;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.HashSet;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@ActiveProfiles(profiles = {"dev"})
//class CartRepositoryTest {
//    private static final long CART_TEST_ID = 1;
//    private static final long USER_TEST_ID = 1;
//    private static final User testUser = new User();
//    private static Cart testNewCart;
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @BeforeEach
//    void setUp() {
//        testUser.setName("Test");
//        testNewCart = new Cart();
//        testNewCart.setCartItems(new HashSet<>());
//        testNewCart.setUser(new User());
//    }
//
//    @Test
//    void testInsertCart() {
//        Cart returnCart = cartRepository.save(testNewCart);
//        Assertions.assertNotNull(returnCart);
//        Assertions.assertTrue(returnCart.getCartId() > 0);
//
//        Optional<Cart> findCart = cartRepository.findById(returnCart.getCartId());
//        Assertions.assertTrue(findCart.isPresent());
//        Assertions.assertEquals(testNewCart.getCartId(), findCart.get().getCartId());
//
//        Assertions.assertEquals(testNewCart, findCart.get());
//    }
//}