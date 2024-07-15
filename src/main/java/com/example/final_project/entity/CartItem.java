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
    @Id
    @Column(name = "CartItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "CartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

    @Column(name = "Quantity")
    private Integer quantity;
}
