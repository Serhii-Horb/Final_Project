package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.dto.responsedDto.StatusResponseDto;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.security.jwt.JwtRequestRefresh;
import com.example.final_project.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
@Tag(name="Order controller.",description="All manipulations with order data are carried out here.")
public class OrderController {
    private final OrderService orderService;

    /**
     * Endpoint to get an order status by id.
     *
     * @param orderId            the ID of the order which is being fetched.
     * @return StatusResponseDto containing the value of the order's status.
     */
    @Operation(
            summary = "shows the status of the specified order."
    )
    @GetMapping("/{orderId}")
    public ResponseEntity<StatusResponseDto> getOrderStatusById(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.getOrderStatusById(orderId),HttpStatus.OK);
    }

    /**
     * Endpoint to get create an order.
     *
     * @param orderRequestDto     the DTO data provided by user.
     * @return The RequestEntity with the body containing the newly created order data.
     */
    @Operation(
            summary = "creates the order."
    )
    @PostMapping
    public ResponseEntity<OrderResponseDto> insertOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(orderService.insertOrder(orderRequestDto),HttpStatus.CREATED);
    }

    /**
     * Endpoint to get order history.
     *
     * @return The ResponseEntity with the body containing the list of orders
     */
    @Operation(
            summary = "shows the orders history of a certain customer."
    )
    @GetMapping("/history")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK);
    }
}
