package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.Product;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.OrderItemRepository;
import com.example.final_project.repository.OrderRepository;
import com.example.final_project.repository.ProductRepository;
import com.example.final_project.repository.UserRepository;
import com.example.final_project.security.jwt.JwtAuthentication;
import com.example.final_project.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final Mappers mappers;

    public Status getOrderStatusById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Requested order was not found")).getStatus();
    }

    public OrderResponseDto insertOrder(OrderRequestDto orderRequestDto) {
        Order order = mappers.convertToOrder(orderRequestDto);
        final JwtAuthentication tokenInfo = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        UserResponseDto user = userService.getByEmail((String) tokenInfo.getPrincipal());
        order.setUser(mappers.convertResponceDTOToUser(user));
        order.setContactPhone(user.getPhoneNumber());
        order.setStatus(Status.CREATED);
        order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        order.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        order = orderRepository.save(order);
        order.getItems().forEach(orderItem -> {
            orderItemRepository.save(orderItem);
        });
        return mappers.convertToOrderResponseDto(orderRepository.save(order));
    }

    public List<OrderResponseDto> getAllOrders() {
        final JwtAuthentication jwtInfoToken = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        UserResponseDto userResponseDto = userService.getByEmail((String) jwtInfoToken.getPrincipal());
        User user = mappers.convertResponceDTOToUser(userResponseDto);
        return MapperUtil.convertList(user.getOrders().stream().toList(),mappers::convertToOrderResponseDto);
    }
}
