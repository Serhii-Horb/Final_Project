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
    @Id
    @Column(name = "FavoriteId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId", nullable = false)
    private Product product;
}
