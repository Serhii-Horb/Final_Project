package com.example.final_project.repository;

import com.example.final_project.entity.Favorite;
import com.example.final_project.entity.Product;
import com.example.final_project.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindByUser_UserIdAndProduct_ProductId_FavoriteExists() {
        User user = new User();
        userRepository.save(user);

        Product product = new Product();
        productRepository.save(product);

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        favoriteRepository.save(favorite);

        Optional<Favorite> foundFavorite = favoriteRepository.findByUser_UserIdAndProduct_ProductId(user.getUserId(), product.getProductId());

        assertTrue(foundFavorite.isPresent());
        assertEquals(user.getUserId(), foundFavorite.get().getUser().getUserId());
        assertEquals(product.getProductId(), foundFavorite.get().getProduct().getProductId());
    }

    @Test
    void testFindByUser_UserIdAndProduct_ProductId_FavoriteDoesNotExist() {
        Optional<Favorite> foundFavorite = favoriteRepository.findByUser_UserIdAndProduct_ProductId(100L, 100L);

        assertFalse(foundFavorite.isPresent());
    }

    @Test
    void testFindByUser_UserId_FavoritesExist() {
        User user = new User();
        userRepository.save(user);

        Product product1 = new Product();
        productRepository.save(product1);

        Product product2 = new Product();
        productRepository.save(product2);

        Favorite favorite1 = new Favorite();
        favorite1.setUser(user);
        favorite1.setProduct(product1);
        favoriteRepository.save(favorite1);

        Favorite favorite2 = new Favorite();
        favorite2.setUser(user);
        favorite2.setProduct(product2);
        favoriteRepository.save(favorite2);

        List<Favorite> favorites = favoriteRepository.findByUser_UserId(user.getUserId());

        assertFalse(favorites.isEmpty());
        assertEquals(2, favorites.size());
        assertTrue(favorites.stream().anyMatch(favorite -> favorite.getProduct().getProductId().equals(product1.getProductId())));
        assertTrue(favorites.stream().anyMatch(favorite -> favorite.getProduct().getProductId().equals(product2.getProductId())));
    }

    @Test
    void testFindByUser_UserId_FavoritesDoNotExist() {
        List<Favorite> favorites = favoriteRepository.findByUser_UserId(100L);

        assertTrue(favorites.isEmpty());
    }
}