package com.example.final_project.repository;

import com.example.final_project.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    /**
     * Finds a {@link Cart} by the associated user's ID.
     *
     * @param userId The ID of the user whose cart is to be retrieved.
     * @return An Optional containing the found Cart, or an empty Optional if no Cart is found for the given user ID.
     */
    Optional<Cart> findByUser_UserId(Long userId);
}