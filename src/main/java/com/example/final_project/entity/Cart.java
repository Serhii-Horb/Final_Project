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
    @Id
    @Column(name = "CartId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User user;
}
