package com.example.final_project.mapper;

import com.example.final_project.dto.FavoritesCreateDto;
import com.example.final_project.dto.FavoritesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.final_project.entity.FavoritesEntity;
import com.example.final_project.entity.FavoritesEntity;

@Mapper(componentModel = "spring")
public interface FavoritesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "userEntity.id")
    @Mapping(source = "productId", target = "productEntity.id")
    FavoritesEntity toEntity(FavoritesCreateDto favoritesCreateDto);

    @Mapping(source = "userEntity.id", target = "userId")
    @Mapping(source = "productEntity.id", target = "productId")
    FavoritesDto toDto(FavoritesEntity favoritesEntity);

    @Mapping(source = "userEntity.id", target = "userId")
    @Mapping(source = "productEntity.id", target = "productId")
    FavoritesDto toDtoWithoutId(FavoritesEntity favoritesEntity);
}