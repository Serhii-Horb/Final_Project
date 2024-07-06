package com.example.final_project.service;

import com.example.final_project.dto.responsedDto.FavoriteResponseDto;
import com.example.final_project.dto.responsedDto.ProductResponseDto;
import com.example.final_project.entity.Favorite;
import com.example.final_project.entity.Product;
import com.example.final_project.entity.User;
import com.example.final_project.exceptions.AuthorizationException;
import com.example.final_project.exceptions.NoUsersFoundException;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.FavoriteRepository;
import com.example.final_project.repository.ProductRepository;
import com.example.final_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final Mappers mappers;
    private final ProductRepository productRepository;

    public void addFavoriteByUserId(Long userId, Long productId) {
        logger.info("Attempting to add favorite to user {} with product {}", userId, productId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logUserNotFoundError(userId);
            return new NoUsersFoundException("Incorrect id of user.");
        });

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            logger.error("Product with id {} not found", productId);
            return new NotFoundInDbException("Incorrect id of product.");
        });

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        try {
            favoriteRepository.save(favorite);
            logger.info("Favorite added successfully for user {} with product {}", userId, productId);
        } catch (Exception exception) {
            logger.error("Error while adding favorite to user {} with product {}", userId, productId);
            throw new RuntimeException("Error while adding favorite to user.");
        }
    }

    public List<FavoriteResponseDto> getAllFavoritesByUserId(Long userId) {
        logger.info("Attempting to get all favorites for user id {}", userId);

        userRepository.findById(userId).orElseThrow(() -> {
            logUserNotFoundError(userId);
            return new AuthorizationException("Incorrect id of user.");
        });

        List<Favorite> favoritesList = favoriteRepository.findByUser_UserId(userId);
        List<FavoriteResponseDto> favoriteResponseDtoList = new ArrayList<>();

        if (!favoritesList.isEmpty()) {
            logger.info("No Favorites found for user {}", userId);
        } else {
            for (Favorite favorite : favoritesList) {
                Product product = favorite.getProduct();
                ProductResponseDto productResponseDto = mappers.convertToProductResponseDto(product);

                FavoriteResponseDto favoriteResponseDto = mappers.convertToFavoriteResponseDto(favorite);
                favoriteResponseDto.setProductResponseDto(productResponseDto);
                favoriteResponseDtoList.add(favoriteResponseDto);
            }
            logger.info("Found {} favorites", favoriteResponseDtoList);
        }
        return favoriteResponseDtoList;
    }

    public void deleteFavoriteByUserId(Long userId, Long productId) {
        logger.info("Attempting to delete favorite for user {} with product {}", userId, productId);

        Favorite favorite = favoriteRepository.findByUser_UserIdAndProduct_ProductId(userId, productId)
                .orElseThrow(() -> {
                    logger.error("Favorite not found for user {} with product {}", userId, productId);
                    return new NotFoundInDbException("Favorite not found.");
                });
        try {
            favoriteRepository.delete(favorite);
            logger.info("Favorite deleted successfully for user {} with product {}", userId, productId);
        } catch (Exception exception) {
            logger.error("Error while deleting favorite for user {} with product {}", userId, productId);
            throw new RuntimeException("Error while deleting favorite for user.");
        }
    }

    private void logUserNotFoundError(Long id) {
        logger.error("User with ID {} not found in DB.", id);
    }
}