package com.example.final_project.repository;

import com.example.final_project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
     @Query("select o from Order o " +
             "where o.deliveryMethod <> SELF_DELIVERY AND " +
             "o.status NOT IN (CANCELED,DELIVERED)")
     List<Order> ordersForSchedulers();
}
