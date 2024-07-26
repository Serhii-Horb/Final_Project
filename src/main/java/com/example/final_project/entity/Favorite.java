package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Favorites")
@Entity
@Getter
@Setter
@ToString(exclude = {"user", "product"})
@EqualsAndHashCode(exclude = {"user", "product"})
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    /**
     * Unique identifier for the favorite entry.
     */
    @Id
    @Column(name = "FavoriteID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    /**
     * The user who marked the product as a favorite.
     * This is a many-to-one relationship where multiple favorites can belong to a single user.
     * The fetch type is set to LAZY to improve performance by loading the user only when needed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    /**
     * The product that has been marked as a favorite.
     * This is a many-to-one relationship where multiple favorites can include the same product.
     * The fetch type is set to LAZY to improve performance by loading the product only when needed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId", nullable = false)
    private Product product;
}
