package com.example.final_project.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteRequestDto {
    /**
     * The ID of the product to be added or removed from favorites.
     * Must be a non-blank, positive numeric value, and should not start with zero.
     *
     * @see #productId
     */
    @NotBlank(message = "Invalid Id: Empty Id") // Ensures that the product ID is not blank.
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    // Ensures the product ID is a positive number and does not start with zero.
    @JsonProperty("cart") // Maps to the "cart" field in JSON.
    private Long productId;

    /**
     * The ID of the user who is adding or removing the product from their favorites.
     * Must be a non-blank, positive numeric value, and should not start with zero.
     *
     * @see #userId
     */
    @NotBlank(message = "Invalid Id: Empty Id") // Ensures that the user ID is not blank.
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    // Ensures the user ID is a positive number and does not start with zero.
    @JsonProperty("user") // Maps to the "user" field in JSON.
    private Long userId;
}
