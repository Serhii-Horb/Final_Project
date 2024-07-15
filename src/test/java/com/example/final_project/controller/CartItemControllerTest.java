package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.CartItemRequestDto;
import com.example.final_project.dto.responsedDto.CartItemResponseDto;
import com.example.final_project.dto.responsedDto.CartResponseDto;
import com.example.final_project.dto.responsedDto.ProductResponseDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.service.CartItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartItemController.class)
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartItemService cartItemServiceMock;
    private CartResponseDto cartResponseDto;
    private CartItemResponseDto cartItemResponseDto;
    private ProductResponseDto productResponseDto;

    private CartItemRequestDto cartItemRequestDto;

    @BeforeEach
    void setUp() {
        cartItemRequestDto = new CartItemRequestDto();
        cartItemRequestDto.setProductId(1L);
        cartItemRequestDto.setQuantity(2);

        cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setCartItemId(1L);
        cartItemResponseDto.setQuantity(10);
        cartItemResponseDto.setCartResponseDto(cartResponseDto);
        cartItemResponseDto.setProductResponseDto(productResponseDto);
    }


    @Test
    void testAddProductInCart() throws Exception {
        Long userId = 1L;

        when(cartItemServiceMock.addOrUpdateProductInCart(any(CartItemRequestDto.class), anyLong()))
                .thenReturn(cartItemResponseDto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/cart/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequestDto))).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProductInCart() throws Exception {
        doNothing().when(cartItemServiceMock).deleteProductInCartByUserIdAndProductId(anyLong(), anyLong());

        this.mockMvc.perform(delete("/cart/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}