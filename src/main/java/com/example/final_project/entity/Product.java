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
    /**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Long productId;

    /**
     * Name of the product.
     */
    @Column(name = "Name")
    private String name;

    /**
     * Description of the product.
     */
    @Column(name = "Description")
    private String description;

    /**
     * Price of the product.
     */
    @Column(name = "Price")
    private BigDecimal price;

    /**
     * Category to which this product belongs.
     * This is a many-to-one relationship with the `Category` entity.
     * The `FetchType.LAZY` fetch type is used to load the category entity only when needed.
     * The `nullable = false` constraint indicates that this field cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category category;

    /**
     * URL of the product image.
     */
    @Column(name = "ImageURL")
    private String imageURL;

    /**
     * Discounted price of the product, if applicable.
     */
    @Column(name = "DiscountPrice")
    private BigDecimal discountPrice;

    /**
     * Timestamp when the product was created.
     * Automatically set to the current timestamp when the product is created.
     */
    @CreationTimestamp
    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    /**
     * Timestamp when the product was last updated.
     * Automatically updated to the current timestamp when the product is modified.
     */
    @UpdateTimestamp
    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    /**
     * Set of cart items that include this product.
     * This is a one-to-many relationship with the `CartItem` entity.
     * `CascadeType.ALL` indicates that all operations (persist, merge, remove, refresh, detach) are cascaded to the cart items.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartItem> cartItem = new HashSet<>();

    /**
     * Set of order items that include this product.
     * This is a one-to-many relationship with the `OrderItem` entity.
     * `CascadeType.ALL` indicates that all operations (persist, merge, remove, refresh, detach) are cascaded to the order items.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    /**
     * Set of favorites that include this product.
     * This is a one-to-many relationship with the `Favorite` entity.
     * `CascadeType.ALL` indicates that all operations (persist, merge, remove, refresh, detach) are cascaded to the favorites.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Favorite> favorites = new HashSet<>();
}
