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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
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

    /**
     * Adds a product to the user's list of favorites.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to be added to favorites.
     */
    public void addFavoriteByUserId(Long userId, Long productId) {
        logInfo("Attempting to add favorite for userId: {} and productId: {}", userId, productId);

        // Fetch the user by ID or throw an exception if not found.
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logError("User with id {} not found.", userId);
            return new NoUsersFoundException("Incorrect id of user.");
        });

        // Fetch the product by ID or throw an exception if not found.
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            logError("Product with id {} not found.", productId);
            return new NotFoundInDbException("Incorrect id of product.");
        });

        // Create a new favorite and set its user and product.
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        // Save the favorite to the repository.
        favoriteRepository.save(favorite);
        logInfo("Favorite successfully added for userId: {} and productId: {}", userId, productId);
    }

    /**
     * Retrieves all favorites for a given user.
     *
     * @param userId The ID of the user.
     * @return A list of FavoriteResponseDto objects representing the user's favorites.
     */
    public List<FavoriteResponseDto> getAllFavoritesByUserId(Long userId) {
        logInfo("Fetching all favorites for userId: {}", userId);

        // Verify that the user exists by ID or throw an exception if not found.
        userRepository.findById(userId).orElseThrow(() -> {
            logError("User with id {} not found.", userId);
            return new AuthorizationException("Incorrect id of user.");
        });

        // Fetch the list of favorites for the given user ID.
        List<Favorite> favoriteList = favoriteRepository.findByUser_UserId(userId);
        List<FavoriteResponseDto> favoriteResponseDtoList = new ArrayList<>();

        // If the list is not empty, convert each favorite to a response DTO and add to the response list.
        if (!favoriteList.isEmpty()) {
            for (Favorite favorite : favoriteList) {
                Product product = favorite.getProduct();
                ProductResponseDto productResponseDto = mappers.convertToProductResponseDto(product);

                FavoriteResponseDto favoriteResponseDto = mappers.convertToFavoriteResponseDto(favorite);
                favoriteResponseDto.setProductResponseDto(productResponseDto);
                favoriteResponseDtoList.add(favoriteResponseDto);
            }
            logInfo("Found {} favorites for userId: {}", favoriteList.size(), userId);
        } else {
            logInfo("No favorites found for userId: {}", userId);
        }

        return favoriteResponseDtoList;
    }

    /**
     * Deletes a favorite product for a given user.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to be removed from favorites.
     */
    public void deleteFavoriteByUserId(Long userId, Long productId) {
        logInfo("Attempting to delete favorite for userId: {} and productId: {}", userId, productId);

        // Fetch the favorite by user ID and product ID or throw an exception if not found.
        Favorite favorite = favoriteRepository.findByUser_UserIdAndProduct_ProductId(userId, productId)
                .orElseThrow(() -> {
                    logError("Favorite for userId: {} and productId: {} not found.", userId, productId);
                    return new NotFoundInDbException("Favorite not found.");
                });

        // Delete the favorite from the repository.
        favoriteRepository.delete(favorite);
        logInfo("Favorite successfully deleted for userId: {} and productId: {}", userId, productId);
    }

    /**
     * Logs an informational message.
     *
     * @param message The message to log.
     * @param args    Arguments for the message.
     */
    private void logInfo(String message, Object... args) {
        logger.info(message, args);
    }

    /**
     * Logs an error message.
     *
     * @param message The message to log.
     * @param args    Arguments for the message.
     */
    private void logError(String message, Object... args) {
        logger.error(message, args);
    }
}