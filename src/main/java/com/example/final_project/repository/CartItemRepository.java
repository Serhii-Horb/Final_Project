package com.example.final_project.repository;

import com.example.final_project.entity.CartItem;
import com.example.final_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);
}
