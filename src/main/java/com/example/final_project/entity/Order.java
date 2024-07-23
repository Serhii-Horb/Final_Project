package com.example.final_project.entity;

import com.example.final_project.entity.enums.Delivery;
import com.example.final_project.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Orders")
@Entity
@Getter
@Setter
@ToString(exclude = {"items", "user"})
@EqualsAndHashCode(exclude = {"items", "user"})
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    /**
     * Unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Long orderId;

    /**
     * Timestamp when the order was created.
     * Automatically generated by Hibernate.
     */
    @CreationTimestamp
    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    /**
     * Address where the order will be delivered.
     */
    @Column(name = "DeliveryAddress")
    private String deliveryAddress;

    /**
     * Contact phone number for the delivery.
     */
    @Column(name = "ContactPhone")
    private String contactPhone;

    /**
     * Method used for delivering the order.
     * This is an enum field, represented as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "DeliveryMethod")
    private Delivery deliveryMethod;

    /**
     * Current status of the order.
     * This is an enum field, represented as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;

    /**
     * Timestamp when the order was last updated.
     * Automatically updated by Hibernate.
     */
    @UpdateTimestamp
    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    /**
     * List of items included in the order.
     * This is a one-to-many relationship with `OrderItem`.
     * The `FetchType.LAZY` fetch type is used to load order items only when needed.
     * The `CascadeType.ALL` cascade type is used to propagate persistence operations to associated `OrderItem` entities.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    /**
     * The user who placed the order.
     * This is a many-to-one relationship with `User`.
     * The `FetchType.LAZY` fetch type is used to load the user entity only when needed.
     * The `nullable = false` constraint indicates that this field cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;
}
