package com.example.final_project.service;

import com.example.final_project.config.MapperUtil;
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
//
//    public List<FavoriteDto> getFavorites() {
//        return MapperUtil.convertList(favoritesRepository.findAll(), mappers::convertToFavoritesDto);
//    }
}
