package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.responsedDto.FavoriteResponseDto;
import com.example.final_project.entity.Favorite;
import com.example.final_project.exceptions.NoFavoritesFoundException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.FavoriteRepository;
import com.example.final_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoritesRepository;
    private final UserRepository usersRepository;
    private final Mappers mappers;


    public List<FavoriteResponseDto> getAllFavorites() {
        List<Favorite> favorites = favoritesRepository.findAll();
        if (favorites.isEmpty()) {
            throw new NoFavoritesFoundException("No favorites found for the user.");
        }
        return MapperUtil.convertList(favoritesRepository.findAll(), mappers::convertToFavoriteResponseDto);
    }
}
