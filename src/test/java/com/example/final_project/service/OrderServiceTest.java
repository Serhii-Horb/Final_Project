package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.OrderItemRequestDto;
import com.example.final_project.dto.requestDto.OrderRequestDto;
import com.example.final_project.dto.responsedDto.OrderResponseDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.Order;
import com.example.final_project.entity.OrderItem;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Delivery;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.OrderItemRepository;
import com.example.final_project.repository.OrderRepository;
import com.example.final_project.security.jwt.JwtAuthentication;
import org.junit.jupiter.api.Assertions;
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

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
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
    @InjectMocks
    private OrderService orderServiceMock;
    private Order order;
    private OrderItem orderItem;
    private OrderRequestDto orderRequestDto;
    private OrderItemRequestDto orderItemRequestDto;
    private User user;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
        order = Order.builder()
                .orderId(1l)
                .status(Status.CREATED)
                .items(List.of(orderItem))
                .build();
        orderItem.setOrder(order);

        user = new User();
        user.setName("Tom");
        user.setPasswordHash("pass1");
        user.setEmail("tom@gmail.com");
        user.setPhoneNumber("+392010");

        orderItemRequestDto = new OrderItemRequestDto();
        orderItemRequestDto.setProductId(2l);
        orderItemRequestDto.setQuantity(5);

        orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderItemsList(List.of(orderItemRequestDto));
        orderRequestDto.setDeliveryAddress("street5");
        orderRequestDto.setDeliveryMethod(Delivery.COURIER_DELIVERY);
    }

    @Test
    void getOrderStatusById() {
        Long orderId = 1l;
        Mockito.when(orderRepositoryMock.findById(orderId))
                .thenReturn(Optional.of(order))
                .thenThrow(NotFoundInDbException.class);
        Status status = orderServiceMock.getOrderStatusById(orderId);
        assertEquals(status,order.getStatus());

        assertThrows(NotFoundInDbException.class,() -> orderServiceMock.getOrderStatusById(orderId));
        Mockito.verify(orderRepositoryMock,Mockito.times(2)).findById(orderId);
    }

    @Test
    void insertOrder() {
        Mockito.when(mappersMock.convertToOrder(Mockito.any(OrderRequestDto.class))).thenReturn(order);
        Mockito.when(mappersMock.convertResponceDTOToUser(Mockito.any(UserResponseDto.class))).thenReturn(user);
        Mockito.when(mappersMock.convertToOrderResponseDto(Mockito.any(Order.class))).thenReturn(new OrderResponseDto());

        Mockito.when(userServiceMock.getByEmail(Mockito.anyString())).thenReturn(new UserResponseDto());
        Mockito.when(orderRepositoryMock.save(Mockito.any(Order.class))).thenReturn(order);
        Mockito.when(orderItemRepositoryMock.save(Mockito.any(OrderItem.class))).thenReturn(orderItem);

        JwtAuthentication tokenInfo = Mockito.mock(JwtAuthentication.class);
        Mockito.when(tokenInfo.getPrincipal()).thenReturn(user.getEmail());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(tokenInfo);

        OrderResponseDto result = orderServiceMock.insertOrder(orderRequestDto);
        Assertions.assertNotNull(result);
        Mockito.verify(mappersMock,Mockito.times(1)).convertToOrder(Mockito.any(OrderRequestDto.class));
        Mockito.verify(securityContext,Mockito.times(1)).getAuthentication();
        Mockito.verify(userServiceMock,Mockito.times(1)).getByEmail(Mockito.anyString());
        Mockito.verify(mappersMock,Mockito.never()).convertToUserResponseDto(Mockito.any(User.class));
        Mockito.verify(orderRepositoryMock,Mockito.times(1)).save(Mockito.any(Order.class));
        Mockito.verify(orderItemRepositoryMock,Mockito.times(order.getItems().size())).save(Mockito.any(OrderItem.class));
        Mockito.verify(mappersMock,Mockito.times(1)).convertToOrderResponseDto(Mockito.any(Order.class));
    }

    @Test
    void getAllOrders() {
        Mockito.when(userServiceMock.getByEmail(Mockito.anyString())).thenReturn(new UserResponseDto());
        Mockito.when(mappersMock.convertResponceDTOToUser(Mockito.any(UserResponseDto.class))).thenReturn(user);

        JwtAuthentication jwtInfoToken = Mockito.mock(JwtAuthentication.class);
        Mockito.when(jwtInfoToken.getPrincipal()).thenReturn(user.getEmail());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(jwtInfoToken);

        MockedStatic<MapperUtil> mockedStatic = Mockito.mockStatic(MapperUtil.class);
        mockedStatic.when(() -> MapperUtil.convertList(Mockito.anyList(),Mockito.any(Function.class)))
                .thenReturn(List.of(new OrderResponseDto()));

        List<OrderResponseDto> allOrders = orderServiceMock.getAllOrders();
        Assertions.assertTrue(!allOrders.isEmpty());
        Assertions.assertEquals(1,allOrders.size());
        Mockito.verify(userServiceMock,Mockito.times(1)).getByEmail(Mockito.anyString());
        Mockito.verify(mappersMock).convertResponceDTOToUser(Mockito.any(UserResponseDto.class));
        mockedStatic.verify(() -> MapperUtil.convertList(Mockito.anyList(),Mockito.any(Function.class)),Mockito.times(1));
    }
}