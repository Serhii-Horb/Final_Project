package com.example.final_project.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequestDto {
    /**
     * The ID of the product being added or updated in the cart.
     * Must be a non-blank, positive numeric value, and should not start with zero.
     *
     * @see #productId
     */
    @NotBlank(message = "Invalid Id: Empty Id") // Ensures that the ID is not blank.
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    // Ensures the ID is a positive number and does not start with zero.
    @JsonProperty("product") // Maps to the "product" field in JSON.
    private Long productId;

    /**
     * The quantity of the product being added or updated in the cart.
     * Must be a non-null, positive integer value.
     *
     * @see #quantity
     */
    @NotNull(message = "Invalid quantity: quantity is NULL") // Ensures that the quantity is not null.
    @Positive(message = "Numeric field value is less than or equal to zero") // Ensures that the quantity is positive.
    @JsonProperty("quantity") // Maps to the "quantity" field in JSON.
    private Integer quantity;
}
