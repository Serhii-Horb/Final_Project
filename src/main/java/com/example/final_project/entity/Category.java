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
    /**
     * Unique identifier for the category.
     */
    @Id
    @Column(name = "CategoryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    /**
     * Name of the category.
     */
    @Column(name = "Name")
    private String name;

    /**
     * Set of products that belong to this category.
     * The relationship is one-to-many, meaning one category can have multiple products.
     * The `cascade = CascadeType.ALL` setting ensures that operations like persist, merge, remove, etc.,
     * are cascaded to the products associated with this category.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();
}
