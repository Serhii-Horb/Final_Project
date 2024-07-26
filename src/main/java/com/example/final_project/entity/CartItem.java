package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "CartItems")
@Entity
@Getter
@Setter
@ToString(exclude = {"cart", "product"})
@EqualsAndHashCode(exclude = {"cart", "product"})
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    /**
     * Unique identifier for the cart item.
     */
    @Id
    @Column(name = "CartItemID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    /**
     * The cart to which this item belongs.
     * The relationship is many-to-one, meaning many cart items can belong to a single cart.
     */
    @ManyToOne
    @JoinColumn(name = "CartId")
    private Cart cart;

    /**
     * The product associated with this cart item.
     * The relationship is many-to-one, meaning many cart items can reference a single product.
     */
    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

    /**
     * Quantity of the product in the cart.
     */
    @Column(name = "Quantity")
    private Integer quantity;
}
