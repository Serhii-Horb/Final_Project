//package com.example.final_project.controller;
//
//import com.example.final_project.dto.CartDto;
//import com.example.final_project.service.CartService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CartController.class)
//class CartControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private CartService cartServiceMock;
//
//    private CartDto cartExpected1, cartExpected2;
//
//    @BeforeEach
//    void setUp() {
//        cartExpected1 = CartDto.builder()
//                .cartId(1L)
//                .user(null)
//                .build();
//        cartExpected2 = CartDto.builder()
//                .cartId(2L)
//                .user(null)
//                .build();
//    }
//
//    @Test
//    void insertCart() throws Exception {
//        when(cartServiceMock.insertCart(any(CartDto.class))).thenReturn(cartExpected1);
//        this.mockMvc.perform(post("/cart")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(cartExpected1)))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.cartId").exists())
//                .andExpect(jsonPath("$.cartId").value(1));
//    }
//}