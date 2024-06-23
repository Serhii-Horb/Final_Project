package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
//    private Set<CartItems> cartItems = new HashSet<>();
//
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User user;
}
