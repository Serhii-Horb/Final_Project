package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.dto.responsedDto.StatusResponseDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Role;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.OrderItemRepository;
import com.example.final_project.repository.OrderRepository;
import com.example.final_project.security.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final Mappers mappers;

    public User fetchUserViaAccessToken() {
        logger.info("Attempting to get a user by the provided access token");
        final JwtAuthentication tokenInfo = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        UserResponseDto userDto = userService.getByEmail((String) tokenInfo.getPrincipal());
        User user = mappers.convertResponceDTOToUser(userDto);
        logger.info("The user with the id of {} has been tracked",user.getUserId());
        return user;
    }

    /**
     * Gets a status of the order fetched by id.
     *
     * @param orderId id of the sought order.
     * @return a status of the fetched order.
     */
    public StatusResponseDto getOrderStatusById(Long orderId) {
        logger.info("Attempting to get the status of the given id");
        User user = fetchUserViaAccessToken();
        Status status;
        if(user.getRole().equals(Role.USER)) {
            for (Order order : user.getOrders()) {
                if (order.getOrderId().equals(orderId)) {
                    status = orderRepository.findById(orderId).get().getStatus();
                    return new StatusResponseDto(status);
                }
            }
            logger.error("Order with the id of {} was not found for ordinary user {}",orderId,user.getEmail());
            throw new NotFoundInDbException("Requested order was not found");
        }
        status = orderRepository.findById(orderId).orElseThrow(() -> {
            logger.error("Order with the id of {} was not found",orderId);
            return new NotFoundInDbException("Requested order was not found");
        }).getStatus();
        logger.info("Order with the id of {} is of {} status",orderId,status);
        return new StatusResponseDto(status);
    }

    /**
     * Creates an order based on the provided DTO data.
     *
     * @param orderRequestDto the DTO data received from user.
     * @return a DTO containing the newly created order data.
     */
    public OrderResponseDto insertOrder(OrderRequestDto orderRequestDto) {
        logger.info("Attempting to create the order");
        User user = fetchUserViaAccessToken();
        Order order = mappers.convertToOrder(orderRequestDto);
        order.setUser(user);
        user.getOrders().add(order);
        order.setContactPhone(user.getPhoneNumber());
        order.setStatus(Status.CREATED);
        order = orderRepository.save(order);
        logger.info("Saving the newly created order with the id of {}",order.getOrderId());
        orderItemRepository.saveAll(order.getItems());

        order.getItems().forEach(item -> {
                    item.setPriceAtPurchase(item.getProduct().getPrice());
                    orderItemRepository.save(item);
                });
        logger.info("Saving the items of the newly created order with the id of {}",order.getOrderId());
        return mappers.convertToOrderResponseDto(order);
    }

    /**
     * Gets the list of orders.
     *
     * @return List<OrderResponseDto>  a list of orders (if requested by user than only their list of orders,
     * otherwise the whole list of all orders).
     */
    public List<OrderResponseDto> getAllOrders() {
        logger.info("Attempting to get the history of the orders");
        User user = fetchUserViaAccessToken();
        if(user.getRole().equals(Role.USER)) {
            logger.info("found {} orders placed by the user the id of {}"
                    ,user.getOrders().size(),user.getUserId());
            return MapperUtil.convertList(user.getOrders().stream().toList(), mappers::convertToOrderResponseDto);
        }
        List<Order> orders = orderRepository.findAll();
        logger.info("found {} placed orders in total");
        return MapperUtil.convertList(orders,mappers::convertToOrderResponseDto);
    }
}
