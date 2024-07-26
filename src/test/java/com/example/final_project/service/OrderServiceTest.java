package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.OrderItemRequestDto;
import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.dto.responsedDto.StatusResponseDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.OrderItem;
import com.example.final_project.entity.Product;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Delivery;
import com.example.final_project.entity.enums.Role;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.OrderItemRepository;
import com.example.final_project.repository.OrderRepository;
import com.example.final_project.security.jwt.JwtAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private OrderItemRepository orderItemRepositoryMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private Mappers mappersMock;
    @Mock
    private Logger logger;
    @InjectMocks
    private OrderService orderServiceMock;
    private Order order, order2;
    private OrderItem orderItem, orderItem2;
    private OrderRequestDto orderRequestDto;
    private OrderItemRequestDto orderItemRequestDto;
    private User user;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
        orderItem2 = new OrderItem();

        order = new Order();
        order.setOrderId(1l);
        order.setStatus(Status.CREATED);
        order.setItems(List.of(orderItem));
        order.setUser(user);

        order2 = new Order();
        order2.setOrderId(2l);
        order2.setStatus(Status.AWAITING_PAYMENT);
        order2.setItems(List.of(orderItem2));

        orderItem.setOrder(order);
        orderItem.setProduct(new Product());
        orderItem2.setOrder(order2);

        user = new User();
        user.setName("Tom");
        user.setPasswordHash("pass1");
        user.setEmail("tom@gmail.com");
        user.setPhoneNumber("+392010");
        user.setRole(Role.USER);
        user.setOrders(Set.of(order));

        orderItemRequestDto = new OrderItemRequestDto();
        orderItemRequestDto.setProductId(2l);
        orderItemRequestDto.setQuantity(5);

        orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderItemsList(List.of(orderItemRequestDto));
        orderRequestDto.setDeliveryAddress("street5");
        orderRequestDto.setDeliveryMethod(Delivery.COURIER_DELIVERY);

        JwtAuthentication jwtAuthentication = Mockito.mock(JwtAuthentication.class);
        when(jwtAuthentication.getPrincipal()).thenReturn(user.getEmail());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(jwtAuthentication);
        SecurityContextHolder.setContext(securityContext);

        when(mappersMock.convertResponceDTOToUser(userResponseDto)).thenReturn(user);
        when(orderServiceMock.fetchUserViaAccessToken()).thenReturn(user);
    }

    @Test
    void getOrderStatusById_ForUser() {
        Long orderId = 1l;

        when(orderRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(order));
        StatusResponseDto status = orderServiceMock.getOrderStatusById(orderId);

        assertEquals(status.getStatus(), order.getStatus());
        assertDoesNotThrow(() -> new NotFoundInDbException("Requested order was not found"));
        verify(orderRepositoryMock).findById(any(Long.class));
    }

    @Test
    void getOrderStatusById_ForUserFailure() {
        Long orderId = 2l;

        when(orderServiceMock.fetchUserViaAccessToken()).thenReturn(user);

        assertThrows(NotFoundInDbException.class, () ->
                orderServiceMock.getOrderStatusById(orderId));
    }

    @Test
    void getOrderStatusById_ForAdmin() {
        Long orderId = 1l;
        user.setRole(Role.ADMINISTRATOR);

        when(orderRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(order));
        StatusResponseDto statusResponseDto = orderServiceMock.getOrderStatusById(orderId);

        assertEquals(statusResponseDto.getStatus(), order.getStatus());
        verify(orderRepositoryMock).findById(any(Long.class));
    }

    @Test
    void insertOrder() {
        user.setOrders(new HashSet<>());

        when(mappersMock.convertToOrder(any(OrderRequestDto.class))).thenReturn(order);
        when(orderRepositoryMock.save(any(Order.class))).thenReturn(order);
        when(orderItemRepositoryMock.save(any())).thenReturn(orderItem);
        when(mappersMock.convertToOrderResponseDto(any(Order.class))).thenReturn(new OrderResponseDto());

        OrderResponseDto result = orderServiceMock.insertOrder(orderRequestDto);
        assertNotNull(result);
        verify(mappersMock).convertToOrder(any(OrderRequestDto.class));
        verify(orderRepositoryMock).save(any(Order.class));
        verify(orderItemRepositoryMock).saveAll(any(List.class));
        verify(mappersMock).convertToOrderResponseDto(any(Order.class));
    }

    @Test
    void getAllOrders_ForUser() {
        MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class);
        mockedStatic.when(() -> MapperUtil.convertList(anyList(), any(Function.class)))
                .thenReturn(user.getOrders().stream().toList());

        List<OrderResponseDto> allOrders = orderServiceMock.getAllOrders();

        assertEquals(1, allOrders.size());
        mockedStatic.verify(() -> MapperUtil.convertList(anyList(), any(Function.class)));
        mockedStatic.closeOnDemand();
    }

    @Test
    void getAllOrders_ForAdmin() {
        user.setRole(Role.ADMINISTRATOR);
        MockedStatic<MapperUtil> mockedStatic = mockStatic(MapperUtil.class);
        mockedStatic.when(() -> MapperUtil.convertList(anyList(), any(Function.class)))
                .thenReturn(List.of(order,order2));

        List<OrderResponseDto> allOrders = orderServiceMock.getAllOrders();

        assertEquals(2, allOrders.size());
        mockedStatic.verify(() -> MapperUtil.convertList(anyList(), any(Function.class)));
    }
}
