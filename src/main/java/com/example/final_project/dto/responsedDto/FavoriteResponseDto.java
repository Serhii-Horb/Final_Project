package com.example.final_project.dto.responsedDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponseDto {
    /**
     * Unique identifier for the favorite item.
     */
    private Long favoriteId;

    /**
     * Identifier for the user who marked the item as a favorite.
     */
    private Long userId;

    /**
     * Product details of the favorite item.
     * This field will be included in the JSON response only if it is not null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product")
    private ProductResponseDto productResponseDto;
}
