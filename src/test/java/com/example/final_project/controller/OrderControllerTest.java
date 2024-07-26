package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.OrderItemRequestDto;
import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.dto.responsedDto.StatusResponseDto;
import com.example.final_project.entity.enums.Delivery;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.security.config.SecurityConfig;
import com.example.final_project.security.jwt.JwtProvider;
import com.example.final_project.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@Import(SecurityConfig.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    @MockBean
    private JwtProvider jwtProvider;
    private OrderResponseDto orderResponseDto;
    private OrderRequestDto orderRequestDto;

    @BeforeEach
    void setUp() {
        orderRequestDto = OrderRequestDto.builder()
                .deliveryAddress("Street 5")
                .deliveryMethod(Delivery.COURIER_DELIVERY)
                .orderItemsList(List.of(new OrderItemRequestDto(4l,5)))
                .build();
        orderResponseDto = OrderResponseDto.builder()
                .orderId(1l)
                .status(Status.CREATED)
                .build();
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void getOrderStatusById() throws Exception {
        when(orderService.getOrderStatusById(any()))
                .thenReturn(new StatusResponseDto(orderResponseDto.getStatus()));
        mockMvc.perform(get("/orders/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CREATED"));
    }
    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void getOrderStatusByIdFailure() throws Exception {
        when(orderService.getOrderStatusById(any()))
                .thenThrow(new NotFoundInDbException("Requested order was not found"));
        mockMvc.perform(get("/orders/{id}",3))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").doesNotExist());
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void insertOrder() throws Exception {
        when(orderService.insertOrder(any(OrderRequestDto.class))).thenReturn(orderResponseDto);
        String jsonContent = objectMapper.writeValueAsString(orderRequestDto)
                .replace("COURIER_DELIVERY","Courier Delivery");
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(1));
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void insertOrderFailure() throws Exception {
        when(orderService.insertOrder(any(OrderRequestDto.class))).thenReturn(orderResponseDto);
        String jsonContent = objectMapper.writeValueAsString(orderRequestDto);
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.orderId").doesNotExist());
    }

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    void getAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(orderResponseDto));
        mockMvc.perform(get("/orders/history"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..orderId").value(1));
    }
}