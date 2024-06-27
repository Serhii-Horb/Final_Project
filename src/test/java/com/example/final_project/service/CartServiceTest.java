//package com.example.final_project.service;
//
//import com.example.final_project.dto.CartDto;
//import com.example.final_project.dto.UserDto;
//import com.example.final_project.entity.Cart;
//import com.example.final_project.entity.User;
//import com.example.final_project.entity.enums.Role;
//import com.example.final_project.mapper.Mappers;
//import com.example.final_project.repository.CartRepository;
//import com.example.final_project.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CartServiceTest {
//
//    @Mock
//    private CartRepository cartRepositoryMock;
//
//    @Mock
//    private UserRepository userRepositoryMock;
//
//    @Mock
//    private Mappers mappersMock;
//
//    @InjectMocks
//    private CartService cartServiceTest;
//
//    private CartDto expectedCartDto;
//    private Cart expectedCart;
//    private User expectedUser;
//
//    @BeforeEach
//    void setUp() {
//        expectedUser = new User(1L);
//
//        expectedCartDto = CartDto.builder()
//                .cartId(1L)
//                .user(new UserDto(1L))
//                .build();
//        expectedCart = new Cart(1L,
//                null,
//                expectedUser
//        );
//    }
//
//    @Test
//    void testInsertCart() {
//        when(mappersMock.convertToCart(any(CartDto.class))).thenReturn(expectedCart);
//        when(cartRepositoryMock.save(any(Cart.class))).thenReturn(expectedCart);
//
//        // When
//        CartDto result = cartServiceTest.insertCart(expectedCartDto);
//
//        // Then
//        assertEquals(expectedCartDto, result);
//        verify(mappersMock, times(1)).convertToCart(expectedCartDto);
//        verify(cartRepositoryMock, times(1)).save(expectedCart);
//    }
//}