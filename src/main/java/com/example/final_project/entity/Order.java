package com.example.final_project.entity;

import com.example.final_project.entity.enums.Delivery;
import com.example.final_project.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.InitBinder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "Orders")
@Entity
@Builder
@Getter
@Setter
@ToString(exclude = {"items", "user"})
@EqualsAndHashCode(exclude = {"items", "user"})
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Long orderId;

    @CreationTimestamp
    @Column(name = "CreatedAt",insertable = false,updatable = false)
    private Timestamp createdAt;

    @Column(name = "DeliveryAddress")
    private String deliveryAddress;

    @Column(name = "ContactPhone")
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "DeliveryMethod")
    private Delivery deliveryMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;

    @UpdateTimestamp
    @Column(name = "UpdatedAt",updatable = false)
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

}
