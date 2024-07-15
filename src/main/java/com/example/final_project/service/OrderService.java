package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.Product;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.OrderItemRepository;
import com.example.final_project.repository.OrderRepository;
import com.example.final_project.repository.ProductRepository;
import com.example.final_project.repository.UserRepository;
import com.example.final_project.security.service.AuthService;
import io.jsonwebtoken.Claims;
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
    private final UserRepository userRepository;
    private final Mappers mappers;

    private final AuthService authService;

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

    public List<OrderResponseDto> getAllOrders(String jwt) {
        if(authService.getJwtProvider().validateRefreshToken(jwt)) {
            final Claims claims = authService.getJwtProvider().getRefreshTokenClaims(jwt);
            final String email = claims.getSubject();
            final String expectedRefreshToken = authService.getRefreshStorage().get(email);
            if(expectedRefreshToken != null && jwt.equals(expectedRefreshToken)) {
                User user = userRepository.findByEmail(email).get();
                List<OrderResponseDto> orderResponseDtoList = MapperUtil.convertList(user.getOrders().stream().toList(), mappers::convertToOrderResponseDto);
                return orderResponseDtoList;
            }
        }
        return null;
    }
}
