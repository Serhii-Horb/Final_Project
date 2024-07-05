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
    @NotBlank(message = "Invalid Id: Empty Id")
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    @JsonProperty("cart")
    private Long productId;

    @NotBlank(message = "Invalid Id: Empty Id")
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    @JsonProperty("user")
    private Long userId;
}
