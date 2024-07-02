package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "Categories")
@Entity
@Getter
@Setter
@ToString(exclude = {"products"})
@EqualsAndHashCode(exclude = {"products"})
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @Column(name = "CategoryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();
}
