package com.example.final_project.service;

import com.example.final_project.dto.OrderDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.enums.Status;
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

    public OrderDto insertOrder(OrderDto orderDto) {
        if(orderDto.getItems().isEmpty()) {
            //exception
        }
        Order order = mappers.convertIntoOrder(orderDto);
        order.setStatus(Status.ORDERED);
        order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        order.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        order.getItems().stream().forEach(e -> orderItemRepository.save(e));
        return mappers.convertIntoOrderDto(orderRepository.save(order));
    }
    public Status getOrderStatusById(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()) {
            return order.get().getStatus();
        } else {
            return null; //exception
        }
    }
}
