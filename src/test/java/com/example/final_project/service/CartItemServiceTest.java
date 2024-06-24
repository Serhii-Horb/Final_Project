package com.example.final_project.service;

import com.example.final_project.dto.CartDto;
import com.example.final_project.dto.CartItemDto;
import com.example.final_project.dto.ProductDto;
import com.example.final_project.entity.Cart;
import com.example.final_project.entity.CartItem;
import com.example.final_project.entity.Product;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CartItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private Mappers mappersMock;
    @InjectMocks
    private CartItemService cartItemServiceTest;

    private CartItemDto cartItemDtoExpected1;
    private CartItemDto cartItemDtoExpected2;

    private CartItem cartItemEntityExpected1;
    private CartItem cartItemEntityExpected2;

    @BeforeEach
    void setUp() {
        cartItemEntityExpected1 = new CartItem(1L, new Cart(2L), new Product(5L), 15);
        cartItemEntityExpected1 = new CartItem(2L, new Cart(3L), new Product(6L), 17);

        cartItemDtoExpected1 = CartItemDto.builder()
                .cartItemId(1L)
                .cart(new CartDto(2L))
                .product(new ProductDto(5L))
                .quantity(15)
                .build();

    }

    @AfterEach
    void tearDown() {
    }
}