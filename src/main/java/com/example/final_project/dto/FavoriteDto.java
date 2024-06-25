package com.example.final_project.dto;

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
public class FavoriteDto {
    private long favoriteId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("User")
    private UserDto users;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Product")
    private long  productId;
}
