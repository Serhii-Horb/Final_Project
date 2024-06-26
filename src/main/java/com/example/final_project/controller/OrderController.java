package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrderController {
//    private final OrderService orderService;
//
//    @PostMapping
//    public ResponseEntity<OrderDto> insertOrder(@RequestBody @Valid OrderDto orderDto) {
//        OrderDto orderDtoResponse = orderService.insertOrder(orderDto);
//        return new ResponseEntity<>(orderDtoResponse, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Status> getOrderStatusById(@PathVariable long id) {
//        Status orderStatusById = orderService.getOrderStatusById(id);
//        return new ResponseEntity<>(orderStatusById,HttpStatus.OK);
//    }

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Status> getOrderStatusById(@PathVariable long id) {
        return new ResponseEntity<>(orderService.getOrderStatusById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> insertOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(orderService.insertOrder(orderRequestDto),HttpStatus.OK);
    }

}
