package com.example.final_project.scheduled;

import com.example.final_project.entity.Order;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private ScheduledService scheduledService;
    private Queue<Order> orders;
    private Order order1,order2;
    private final Timestamp UPDATED_AT_INITIAL = Timestamp.from(LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC));
    @BeforeEach
    void setUp() {
        orders = new ConcurrentLinkedQueue<>();
        order1 = new Order();
        order1.setStatus(Status.CREATED);
        order1.setUpdatedAt(UPDATED_AT_INITIAL);
        order2 = new Order();
        order2.setStatus(Status.PAID);
        order1.setUpdatedAt(UPDATED_AT_INITIAL);
    }

    @Test
    void initializeQueue() {
        when(orderRepository.ordersForSchedulers()).thenReturn(List.of(order1,order2));
        orders = new ConcurrentLinkedQueue<>(orderRepository.ordersForSchedulers());
        assertEquals(2,orders.size());
    }

    @Test
    void changeStatusTask() {
        orders.add(order1);
        orders.add(order2);

        when(orderRepository.ordersForSchedulers()).thenReturn(new ArrayList<>(orders));
        scheduledService.initializeQueue();

        scheduledService.changeStatusTask();

        assertTrue(order1.getStatus() == Status.AWAITING_PAYMENT || order1.getStatus() == Status.CANCELED);
        assertTrue(order2.getStatus() == Status.ON_THE_WAY);
        assertNotEquals(order1.getUpdatedAt(),UPDATED_AT_INITIAL);
        verify(orderRepository, times(2)).save(any(Order.class));
    }
    @Test
    void changeStatusTask_ShouldHandleEmptyQueue() {
        when(orderRepository.ordersForSchedulers()).thenReturn(new ArrayList<>());

        scheduledService.initializeQueue();
        scheduledService.changeStatusTask();

        verify(orderRepository, never()).save(any(Order.class));
    }
}