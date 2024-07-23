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

    /**
     * Endpoint to add a product to the user's list of favorite products.
     *
     * @param userId    the ID of the user who is adding the favorite product.
     * @param productId the ID of the product to be added to favorites.
     */
    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Add favorite.",
            description = "Allows you to add a new favorite product."
    )
    public void addFavoriteByUserId(@PathVariable @Valid @Min(1) Long userId,
                                    @RequestBody @Valid @Min(1) Long productId) {
        favoritesService.addFavoriteByUserId(userId, productId);
    }

    /**
     * Endpoint to remove a product from the user's list of favorite products.
     *
     * @param userId    the ID of the user who is deleting the favorite product.
     * @param productId the ID of the product to be removed from favorites.
     */
    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Delete favorite.",
            description = "Allows you to delete a favorite product."
    )
    public void deleteFavoriteByProductId(@PathVariable @Valid @Min(1) Long userId,
                                          @RequestBody @Valid @Min(1) Long productId) {
        favoritesService.deleteFavoriteByUserId(userId, productId);
    }

    /**
     * Endpoint to retrieve all favorite products for a specific user.
     *
     * @param userId the ID of the user whose favorite products are being retrieved.
     * @return a list of favorite products for the specified user.
     */
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
