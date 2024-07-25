package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "Cart")
@Entity
@Getter
@Setter
@ToString(exclude = {"cartItems", "user"})
@EqualsAndHashCode(exclude = {"cartItems", "user"})
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    /**
     * Unique identifier for the cart.
     */
    @Id
    @Column(name = "CartId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    /**
     * Set of items associated with this cart.
     * The cart items are cascaded so that operations on the cart are also applied to the items.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    /**
     * User associated with this cart.
     * This relationship is lazily loaded, meaning it will be loaded on demand.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User user;
}
