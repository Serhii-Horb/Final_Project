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

    public void addFavoriteByUserId(Long userId, Long productId) {
        logInfo("Attempting to add favorite for userId: {} and productId: {}", userId, productId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logError("User with id {} not found.", userId);
            return new NoUsersFoundException("Incorrect id of user.");
        });

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            logError("Product with id {} not found.", productId);
            return new NotFoundInDbException("Incorrect id of product.");
        });

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        favoriteRepository.save(favorite);
        logInfo("Favorite successfully added for userId: {} and productId: {}", userId, productId);
    }

    public List<FavoriteResponseDto> getAllFavoritesByUserId(Long userId) {
        logInfo("Fetching all favorites for userId: {}", userId);

        userRepository.findById(userId).orElseThrow(() -> {
            logError("User with id {} not found.", userId);
            return new AuthorizationException("Incorrect id of user.");
        });

        List<Favorite> favoriteList = favoriteRepository.findByUser_UserId(userId);
        List<FavoriteResponseDto> favoriteResponseDtoList = new ArrayList<>();

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

    public void deleteFavoriteByUserId(Long userId, Long productId) {
        logInfo("Attempting to delete favorite for userId: {} and productId: {}", userId, productId);

        Favorite favorite = favoriteRepository.findByUser_UserIdAndProduct_ProductId(userId, productId)
                .orElseThrow(() -> {
                    logError("Favorite for userId: {} and productId: {} not found.", userId, productId);
                    return new NotFoundInDbException("Favorite not found.");
                });

        favoriteRepository.delete(favorite);
        logInfo("Favorite successfully deleted for userId: {} and productId: {}", userId, productId);
    }

    private void logInfo(String message, Object... args) {
        logger.info(message, args);
    }

    private void logError(String message, Object... args) {
        logger.error(message, args);
    }
}