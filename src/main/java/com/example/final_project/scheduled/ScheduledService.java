package com.example.final_project.scheduled;

import com.example.final_project.entity.Order;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
/**
 * Service to manage scheduled tasks for order status changes.
 */
@Service
@RequiredArgsConstructor
public class ScheduledService {
    private final OrderRepository orderRepository;
    private Queue<Order> orders;
    private Random random = new Random();
    private static final Map<Status, Status[]> STATUS_TRANSITIONS = new EnumMap<>(Status.class);
    /**
     * Map defining the possible status transitions for each status.
     */
    static {
        STATUS_TRANSITIONS.put(Status.CREATED, new Status[]{Status.AWAITING_PAYMENT, Status.CANCELED});
        STATUS_TRANSITIONS.put(Status.AWAITING_PAYMENT, new Status[]{Status.PAID, Status.CANCELED});
        STATUS_TRANSITIONS.put(Status.PAID, new Status[]{Status.ON_THE_WAY});
        STATUS_TRANSITIONS.put(Status.ON_THE_WAY, new Status[]{Status.DELIVERED});
    }

    /**
     * Initializes the queue with orders from the repository.
     * This method is called after the bean's properties have been initialized.
     */
    @PostConstruct
    public void initializeQueue() {
        orders = new ConcurrentLinkedQueue<>(orderRepository.ordersForSchedulers());
    }

    /**
     * Task to change the status of orders at a fixed rate.
     * This method is executed asynchronously.
     */
    @Async
    @Scheduled(fixedRate = 30000)
    public void changeStatusTask() {
        while (!orders.isEmpty()) {
            Order order = orders.poll();
            Status status = order.getStatus();
            Status[] possibleStatuses = STATUS_TRANSITIONS.get(status);
                if (possibleStatuses.length == 2) {
                    order.setStatus(possibleStatuses[random.nextInt(2)]);
                } else {
                    order.setStatus(possibleStatuses[0]);
                }
                order.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                orderRepository.save(order);
        }
        initializeQueue();
    }
}
