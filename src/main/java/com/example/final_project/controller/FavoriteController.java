package com.example.final_project.controller;

import com.example.final_project.dto.responsedDto.FavoriteResponseDto;
import com.example.final_project.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/favorites")
public class FavoriteController {
    private final FavoriteService favoritesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteResponseDto> getAllFavorites() {
        return favoritesService.getAllFavorites();
    }
}
