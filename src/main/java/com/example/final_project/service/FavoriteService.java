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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final Mappers mappers;
    private final ProductRepository productRepository;

    public void addFavoriteByUserId(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoUsersFoundException("Incorrect id of user."));
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundInDbException("Incorrect id of product."));
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        favoriteRepository.save(favorite);
    }

    public List<FavoriteResponseDto> getAllFavoritesByUserId(Long id) {
        userRepository.findById(id).orElseThrow(() -> new AuthorizationException("Incorrect id of user."));
        List<Favorite> favoriteList = favoriteRepository.findByUser_UserId(id);
        List<FavoriteResponseDto> favoriteResponseDtoList = new ArrayList<>();
        if (!favoriteList.isEmpty()) {
            for (Favorite favorite : favoriteList) {
                Product product = favorite.getProduct();
                ProductResponseDto productResponseDto = mappers.convertToProductResponseDto(product);

                FavoriteResponseDto favoriteResponseDto = mappers.convertToFavoriteResponseDto(favorite);
                favoriteResponseDto.setProductResponseDto(productResponseDto);
                favoriteResponseDtoList.add(favoriteResponseDto);
            }
        }
        return favoriteResponseDtoList;
    }

    public void deleteFavoriteByUserId(Long userId, Long productId) {
        Favorite favorite = favoriteRepository.findByUser_UserIdAndProduct_ProductId(userId, productId)
                .orElseThrow(() -> new NotFoundInDbException("Favorite not found."));
        favoriteRepository.delete(favorite);
    }
}