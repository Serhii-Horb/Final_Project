package com.example.final_project.repository;

import com.example.final_project.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    /**
     * Finds a {@link CartItem} by its associated Cart and Product IDs.
     *
     * @param cartId    The ID of the cart.
     * @param productId The ID of the product.
     * @return An Optional containing the found CartItem, or an empty Optional if no CartItem is found with the given Cart and Product IDs.
     */
    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);
}
