package com.example.final_project.repository;

import com.example.final_project.entity.Order;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Delivery;
import com.example.final_project.entity.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    private Order order1;
    private Order order2;
    private Order order3;
    @BeforeEach
    void setUp() {
        order1 = Order.builder()
                .status(Status.CREATED)
                .deliveryMethod(Delivery.COURIER_DELIVERY)
                .build();
        order2 = Order.builder()
                .status(Status.CANCELED)
                .deliveryMethod(Delivery.COURIER_DELIVERY)
                .build();
        order3 = Order.builder()
                .status(Status.PAID)
                .deliveryMethod(Delivery.SELF_DELIVERY)
                .build();
    }
    @Test
    void ordersForSchedulers() {
        Order saved1 = orderRepository.save(order1);
        Order saved2 = orderRepository.save(order2);
        Order saved3 = orderRepository.save(order3);
        List<Order> orders = orderRepository.ordersForSchedulers();
        Assertions.assertTrue(orders.contains(saved1));
        Assertions.assertFalse(orders.contains(saved2));
        Assertions.assertFalse(orders.contains(saved3));
    }
}