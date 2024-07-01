package com.example.final_project.service;

import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.OrderItemRepository;
import com.example.final_project.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final Mappers mappers;
    public Status getOrderStatusById(long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()) {
            return orderOptional.get().getStatus();
        }
        throw new NotFoundInDbException("Requested order was not found");
    }
    public OrderResponseDto insertOrder(OrderRequestDto orderRequestDto) {
        Order order = mappers.convertToOrder(orderRequestDto);
        order.setStatus(Status.DELIVERED);
        order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        order.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        order.getItems().forEach(item -> orderItemRepository.save(item));
        return mappers.convertToOrderResponseDto(orderRepository.save(order));
    }
}
