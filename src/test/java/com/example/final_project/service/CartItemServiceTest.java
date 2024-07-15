package com.example.final_project.service;

import com.example.final_project.dto.requestDto.CartItemRequestDto;
import com.example.final_project.dto.responsedDto.CartItemResponseDto;
import com.example.final_project.dto.responsedDto.CartResponseDto;
import com.example.final_project.dto.responsedDto.ProductResponseDto;
import com.example.final_project.entity.*;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CartItemRepository;
import com.example.final_project.repository.CartRepository;
import com.example.final_project.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {
    @Mock
    private CartItemRepository cartItemRepositoryMock;

    @Mock
    private CartRepository cartRepositoryMock;

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private CartItemService cartItemService;

    private Cart cart;
    private Product product;
    private CartItem cartItem;
    private CartItemRequestDto cartItemRequestDto;
    private CartItemResponseDto cartItemResponseDto;
    private CartResponseDto cartResponseDto;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    public void setUp() {
        cart = new Cart(1L, new HashSet<>(), new User());
        product = new Product(1L, "Product", "Description", BigDecimal.valueOf(10.00),
                new Category(), "imageUrl", BigDecimal.valueOf(9.00), null, null, null,
                null, null);
        cartItem = new CartItem(1L, cart, product, 10);
        cartItemRequestDto = new CartItemRequestDto(1L, 2);
        cartItemResponseDto = new CartItemResponseDto(1L, 10, cartResponseDto, productResponseDto);
    }

    @Test
    void testAddOrUpdateProductInCart_NewItem() {
        when(cartRepositoryMock.findByUser_UserId(1L)).thenReturn(Optional.of(cart));
        when(productRepositoryMock.findById(1L)).thenReturn(Optional.of(product));
        when(cartItemRepositoryMock.findByCart_CartIdAndProduct_ProductId(1L, 1L)).thenReturn(Optional.empty());
        when(cartItemRepositoryMock.save(any(CartItem.class))).thenAnswer(invocation -> {
            CartItem savedCartItem = invocation.getArgument(0);
            savedCartItem.setCartItemId(1L);
            return savedCartItem;
        });
        when(mappersMock.convertToCartItemResponseDto(any(CartItem.class))).thenReturn(cartItemResponseDto);

        CartItemResponseDto response = cartItemService.addOrUpdateProductInCart(cartItemRequestDto, 1L);

        assertNotNull(response);
        assertEquals(cartItemResponseDto, response);

        verify(productRepositoryMock, times(1)).findById(1L);
        verify(cartItemRepositoryMock, times(1)).findByCart_CartIdAndProduct_ProductId(1L, 1L);
        verify(cartItemRepositoryMock, times(1)).save(any(CartItem.class));
        verify(mappersMock, times(1)).convertToCartItemResponseDto(any(CartItem.class));
    }

    @Test
    void testAddOrUpdateProductInCart_UpdateItem() {
        cartItem.setQuantity(3);
        when(cartRepositoryMock.findByUser_UserId(1L)).thenReturn(Optional.of(cart));
        when(productRepositoryMock.findById(1L)).thenReturn(Optional.of(product));
        when(cartItemRepositoryMock.findByCart_CartIdAndProduct_ProductId(1L, 1L)).thenReturn(Optional.of(cartItem));
        when(cartItemRepositoryMock.save(any(CartItem.class))).thenReturn(cartItem);
        when(mappersMock.convertToCartItemResponseDto(any(CartItem.class))).thenReturn(cartItemResponseDto);

        CartItemResponseDto response = cartItemService.addOrUpdateProductInCart(cartItemRequestDto, 1L);

        assertNotNull(response);
        assertEquals(cartItemResponseDto, response);

        verify(cartRepositoryMock, times(1)).findByUser_UserId(1L);
        verify(productRepositoryMock, times(1)).findById(1L);
        verify(cartItemRepositoryMock, times(1)).findByCart_CartIdAndProduct_ProductId(1L, 1L);
        verify(cartItemRepositoryMock, times(1)).save(any(CartItem.class));
        verify(mappersMock, times(1)).convertToCartItemResponseDto(any(CartItem.class));
    }

    @Test
    void testAddOrUpdateProductInCart_CartNotFound() {
        when(cartRepositoryMock.findByUser_UserId(1L)).thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> cartItemService.addOrUpdateProductInCart(cartItemRequestDto, 1L));
        assertEquals("Cart not found for userId: 1", exception.getMessage());

        verify(cartRepositoryMock, times(1)).findByUser_UserId(1L);
        verify(productRepositoryMock, times(0)).findById(anyLong());
        verify(cartItemRepositoryMock, times(0)).findByCart_CartIdAndProduct_ProductId(anyLong(), anyLong());
        verify(cartItemRepositoryMock, times(0)).save(any(CartItem.class));
        verify(mappersMock, times(0)).convertToCartItemResponseDto(any(CartItem.class));
    }

    @Test
    void testAddOrUpdateProductInCart_ProductNotFound() {
        when(cartRepositoryMock.findByUser_UserId(1L)).thenReturn(Optional.of(cart));
        when(productRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> cartItemService.addOrUpdateProductInCart(cartItemRequestDto, 1L));
        assertEquals("Product not found for productId: 1", exception.getMessage());

        verify(cartRepositoryMock, times(1)).findByUser_UserId(1L);
        verify(productRepositoryMock, times(1)).findById(1L);
        verify(cartItemRepositoryMock, times(0)).findByCart_CartIdAndProduct_ProductId(anyLong(), anyLong());
        verify(cartItemRepositoryMock, times(0)).save(any(CartItem.class));
        verify(mappersMock, times(0)).convertToCartItemResponseDto(any(CartItem.class));
    }

    @Test
    void testDeleteProductInCartByUserIdAndProductId_Success() {
        when(cartRepositoryMock.findByUser_UserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepositoryMock.findByCart_CartIdAndProduct_ProductId(1L, 1L)).thenReturn(Optional.of(cartItem));

        assertDoesNotThrow(() -> cartItemService.deleteProductInCartByUserIdAndProductId(1L, 1L));

        verify(cartRepositoryMock, times(1)).findByUser_UserId(1L);
        verify(cartItemRepositoryMock, times(1)).findByCart_CartIdAndProduct_ProductId(1L, 1L);
        verify(cartItemRepositoryMock, times(1)).delete(cartItem);
    }
}