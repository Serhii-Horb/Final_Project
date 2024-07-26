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
@Builder
public class OrderItem {
    /**
     * Unique identifier for the order item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemID")
    private Long orderItemId;

    /**
     * Quantity of the product ordered.
     */
    @Column(name = "Quantity")
    private int quantity;

    /**
     * Price of the product at the time of purchase.
     */
    @Column(name = "PriceAtPurchase")
    private BigDecimal priceAtPurchase;

    /**
     * The order to which this item belongs.
     * This is a many-to-one relationship with the `Order` entity.
     * The `FetchType.LAZY` fetch type is used to load the order entity only when needed.
     * The `nullable = false` constraint indicates that this field cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;

    /**
     * The product that was ordered.
     * This is a many-to-one relationship with the `Product` entity.
     * The `FetchType.LAZY` fetch type is used to load the product entity only when needed.
     * The `nullable = false` constraint indicates that this field cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;
}
