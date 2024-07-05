package com.example.final_project.repository;

import com.example.final_project.entity.CartItem;
import com.example.final_project.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUser_UserIdAndProduct_ProductId(Long userId, Long productId);

    List<Favorite> findByUser_UserId(Long userId);
}
