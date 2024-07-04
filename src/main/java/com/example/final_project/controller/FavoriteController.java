package com.example.final_project.controller;

import com.example.final_project.dto.responsedDto.FavoriteResponseDto;
import com.example.final_project.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/favorites")
@Tag(name = "Favorite controller.", description = "You can work with favorite products using this controller.")
public class FavoriteController {
    private final FavoriteService favoritesService;

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Add favorite.",
            description = "Allows you to add a new favorite product."
    )
    public void addFavoriteByUserId(@PathVariable @Valid @Min(1) Long userId, @RequestBody @Valid @Min(1) Long productId) {
        favoritesService.addFavoriteByUserId(userId, productId);
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Delete favorite.",
            description = "Allows you to delete a favorite product."
    )
    public void deleteFavoriteByProductId(@PathVariable @Valid @Min(1) Long userId, @RequestBody @Valid @Min(1) Long productId) {
        favoritesService.deleteFavoriteByUserId(userId, productId);
    }

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all favorites.",
            description = "Allows you to get all favorite products by UserId."
    )
    public List<FavoriteResponseDto> getAllFavoritesByUserId(@PathVariable @Valid @Min(1) Long userId) {
        return favoritesService.getAllFavoritesByUserId(userId);
    }
}
