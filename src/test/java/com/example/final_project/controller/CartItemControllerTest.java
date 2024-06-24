//package com.example.final_project.controller;
//
//import com.example.final_project.dto.CartDto;
//import com.example.final_project.dto.CartItemDto;
//import com.example.final_project.dto.ProductDto;
//import com.example.final_project.service.CartItemService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CartItemControllerTest.class)
//class CartItemControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private CartItemService cartItemServiceMock;
//
//    private CartItemDto cartItemExpected1;
//    private CartItemDto cartItemExpected2;

//    @BeforeEach
//    void setUp() {
//        cartItemExpected1 = CartItemDto.builder()
//                .cartItemId(1L)
//                .cart(new CartDto())
//                .product(new ProductDto(2L))
//                .quantity(12)
//                .build();
//        cartItemExpected2 = CartItemDto.builder()
//                .cartItemId(2L)
//                .cart(new CartDto())
//                .product(new ProductDto(4L))
//                .quantity(11)
//                .build();
//    }

//    @Test
//    void getCartItemTest() throws Exception {
//        when(cartItemServiceMock.getCartItem()).thenReturn(List.of(cartItemExpected1, cartItemExpected2));
//        this.mockMvc.perform(get("/cartitem")).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$..id").exists())
//                .andExpect(jsonPath("$..quantity").exists());
//    }

//    @Test
//    void insertCartItemTest() throws Exception {
//        CartItemDto cartItemInsert = CartItemDto.builder()
//                .cart(new CartDto())
//                .product(new ProductDto(5L))
//                .quantity(9)
//                .build();
//        when(cartItemServiceMock.insertCartItem(any(CartItemDto.class))).thenReturn(cartItemExpected2);
//
//        String requestBody = objectMapper.writeValueAsString(cartItemInsert);
//
//        this.mockMvc.perform(post("/cartitem")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.cartItemId").exists())
//                .andExpect(jsonPath("$.cartItemId").value(2))
//                .andExpect(jsonPath("$.quantity").value(cartItemInsert.getQuantity()));
//    }
//
//    @Test
//    void deleteCartItemByIdTest() throws Exception {
//        when(cartItemServiceMock.getCartItemById(2L)).thenReturn(cartItemExpected2);
//        this.mockMvc.perform(delete("/cartitem/{id}", 2)).andDo(print())
//                .andExpect(status().isNoContent())
//                .andExpect(jsonPath("$.id").doesNotExist());
//    }
//
//    @Test
//    void updateCartItemTest() throws Exception {
//        CartItemDto cartItemUpdate = CartItemDto.builder()
//                .cartItemId(1L)
//                .cart(new CartDto())
//                .product(new ProductDto(5L))
//                .quantity(8)
//                .build();
//        when(cartItemServiceMock.updateCartItem(any(CartItemDto.class))).thenReturn(cartItemUpdate);
//
//        String requestBody = objectMapper.writeValueAsString(cartItemUpdate);
//
//        this.mockMvc.perform(put("/cartitem")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.cartItemId").exists())
//                .andExpect(jsonPath("$.cartItemId").value(1))
//                .andExpect(jsonPath("$.quantity").value(cartItemUpdate.getQuantity()));
//    }
//}
