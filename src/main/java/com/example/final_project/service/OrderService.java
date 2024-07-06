package com.example.final_project.service;

import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.Product;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.OrderItemRepository;
import com.example.final_project.repository.OrderRepository;
import com.example.final_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final Mappers mappers;

//    public Status getOrderStatusById(long id) {
//        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id of user."));
//        return order.getStatus();
//
//        Optional<Order> orderOptional = orderRepository.findById(id);
//        if(orderOptional.isPresent()) {
//            return orderOptional.get().getStatus();
//        }
//        throw new NotFoundInDbException("Requested order was not found");

    public Status getOrderStatusById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Requested order was not found")).getStatus();

    }
    public OrderResponseDto insertOrder(OrderRequestDto orderRequestDto) {
        Order order = mappers.convertToOrder(orderRequestDto);
        order.setStatus(Status.DELIVERED);
        order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        order.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        order = orderRepository.save(order);

        final Order savedOrder = order;
        order.getItems().forEach(orderItem ->  {
            orderItem.setOrder(savedOrder);
            Product product = productRepository.findById(orderItem.getProduct().getProductId()).orElseThrow(() -> new NotFoundInDbException("Requested product was not found"));
            orderItem.setProduct(product);
            orderItemRepository.save(orderItem);
        });
        return mappers.convertToOrderResponseDto(orderRepository.save(order));
    }

    public List<OrderResponseDto> getAllOrders() {
        return null;
    }
}
