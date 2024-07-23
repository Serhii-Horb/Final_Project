package com.example.final_project.entity;

import com.example.final_project.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "Users")
@Entity
@Getter
@Setter
@ToString(exclude = {"role", "cart", "favorites", "orders"})
@EqualsAndHashCode(exclude = {"role", "cart", "favorites", "orders"})
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * Name of the user.
     * This field cannot be null.
     */
    @Column(name = "Name", nullable = false)
    private String name;

    /**
     * Email address of the user.
     * This field must be unique and cannot be null.
     */
    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    /**
     * Phone number of the user.
     */
    @Column(name = "PhoneNumber")
    private String phoneNumber;

    /**
     * Password hash of the user.
     * This field cannot be null.
     */
    @Column(name = "PasswordHash", nullable = false)
    private String passwordHash;

    /**
     * Refresh token associated with the user.
     */
    @Column(name = "RefreshToken")
    private String refreshToken;

    /**
     * Role of the user.
     * This field cannot be null.
     * The role is represented as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private Role role;

    /**
     * Cart associated with the user.
     * This is a one-to-one relationship with the `Cart` entity.
     * `FetchType.LAZY` is used to load the cart entity only when needed.
     * `CascadeType.ALL` indicates that all operations (persist, merge, remove, refresh, detach) are cascaded to the cart.
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    /**
     * Set of favorites associated with the user.
     * This is a one-to-many relationship with the `Favorite` entity.
     * `FetchType.LAZY` is used to load favorites only when needed.
     * `CascadeType.ALL` indicates that all operations (persist, merge, remove, refresh, detach) are cascaded to the favorites.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Favorite> favorites = new HashSet<>();

    /**
     * Set of orders placed by the user.
     * This is a one-to-many relationship with the `Order` entity.
     * `FetchType.LAZY` is used to load orders only when needed.
     * `CascadeType.ALL` indicates that all operations (persist, merge, remove, refresh, detach) are cascaded to the orders.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();
}

