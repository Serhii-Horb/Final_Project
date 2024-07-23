package com.example.final_project.repository;

import com.example.final_project.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    /**
     * Finds a {@link Favorite} by the associated user's ID and product's ID.
     *
     * @param userId    The ID of the user who has favorited the product.
     * @param productId The ID of the product that is favorited.
     * @return An Optional containing the found Favorite, or an empty Optional if no Favorite is found for the given
     * user ID and product ID.
     */
    Optional<Favorite> findByUser_UserIdAndProduct_ProductId(Long userId, Long productId);

    /**
     * Finds all {@link Favorite} entities associated with a specific user's ID.
     *
     * @param userId The ID of the user whose favorites are to be retrieved.
     * @return A List of Favorites associated with the given user ID. This list may be empty if no favorites are found.
     */
    List<Favorite> findByUser_UserId(Long userId);
}
