package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "OrderItems")
@Entity
@Getter
@Setter
@ToString(exclude = {"order", "product"})
@EqualsAndHashCode(exclude = {"order", "product"})
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemId")
    private Long orderItemId;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "PriceAtPurchase")
    private BigDecimal priceAtPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId", nullable = false)
    private Product product;
}
