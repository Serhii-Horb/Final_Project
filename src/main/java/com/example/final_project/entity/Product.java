package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Table(name = "Products")
@Entity
@Getter
@Setter
@ToString(exclude = {"category", "cartItem", "orderItems", "favorites"})
@EqualsAndHashCode(exclude = {"category", "cartItem", "orderItems", "favorites"})
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId")
    private Long productId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category category;

    @Column(name = "ImageURL")
    private String imageURL;

    @Column(name = "DiscountPrice")
    private BigDecimal discountPrice;

    @CreationTimestamp
    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartItem> cartItem = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Favorite> favorites = new HashSet<>();
}
