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
public class CartRequestDto {
    /**
     * The ID of the user associated with the cart.
     * Must be a non-blank, positive numeric value, and should not start with zero.
     *
     * @see #userId
     */
    @NotBlank(message = "Invalid Id: Empty Id")
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    @JsonProperty("user")
    private Long userId;
}
