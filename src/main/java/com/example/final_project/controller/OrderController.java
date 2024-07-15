package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
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

    @Operation(
            summary = "shows the status of the specified order."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Status> getOrderStatusById(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.getOrderStatusById(id),HttpStatus.OK);
    }

    @Operation(
            summary = "creates the order."
    )
    @PostMapping
    public ResponseEntity<OrderResponseDto> insertOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(orderService.insertOrder(orderRequestDto),HttpStatus.CREATED);
    }

    @Operation(
            summary = "shows the orders history of a certain customer."
    )
    @GetMapping("/history")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@RequestBody JwtRequestRefresh jwt) {
        return new ResponseEntity<>(orderService.getAllOrders(jwt.getRefreshToken()),HttpStatus.OK);
    }
}
