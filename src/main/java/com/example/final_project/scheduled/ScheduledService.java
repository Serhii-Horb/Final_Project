package com.example.final_project.scheduled;

import com.example.final_project.entity.Order;
import com.example.final_project.entity.enums.Status;
import com.example.final_project.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduledService {
    private final OrderRepository orderRepository;
    private Queue<Order> orders;
    private Random random = new Random();
    private static final Map<Status, Status[]> STATUS_TRANSITIONS = new EnumMap<>(Status.class);

    static {
        STATUS_TRANSITIONS.put(Status.CREATED, new Status[]{Status.AWAITING_PAYMENT, Status.CANCELED});
        STATUS_TRANSITIONS.put(Status.AWAITING_PAYMENT, new Status[]{Status.PAID, Status.CANCELED});
        STATUS_TRANSITIONS.put(Status.PAID, new Status[]{Status.ON_THE_WAY});
        STATUS_TRANSITIONS.put(Status.ON_THE_WAY, new Status[]{Status.DELIVERED});
        STATUS_TRANSITIONS.put(Status.DELIVERED, null);
        STATUS_TRANSITIONS.put(Status.CANCELED, null);
    }

    @PostConstruct
    public void initializeQueue() {
        orders = orderRepository.findAll().stream().collect(Collectors.toCollection(() -> new ConcurrentLinkedQueue<>()));
    }

    @Async
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void changeStatusTask() {
        while (!orders.isEmpty()) {
            Order order = orders.poll();
            Status status = order.getStatus();
            Status[] possibleStatuses = STATUS_TRANSITIONS.get(status);
            if (possibleStatuses != null) {
                if (possibleStatuses.length == 2) {
                    order.setStatus(possibleStatuses[random.nextInt(2)]);
                } else {
                    order.setStatus(possibleStatuses[0]);
                }
                orderRepository.save(order);
            }
        }
        initializeQueue();
    }
}
