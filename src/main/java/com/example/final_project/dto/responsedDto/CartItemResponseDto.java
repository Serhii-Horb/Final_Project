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
public class CartItemResponseDto {
    /**
     * Unique identifier for the cart item.
     *
     * @see #cartItemId
     */
    private Long cartItemId;

    /**
     * Quantity of the product in the cart item.
     *
     * @see #quantity
     */
    private Integer quantity;

    /**
     * Details of the associated cart.
     * Included in the response only if the value is not null.
     *
     * @see CartResponseDto
     * @see #cartResponseDto
     */
    @JsonInclude(JsonInclude.Include.NON_NULL) // Includes the cart details only if not null in the JSON response.
    @JsonProperty("cart") // Maps the JSON property "cart" to this field.
    private CartResponseDto cartResponseDto;

    /**
     * Details of the associated product.
     * Included in the response only if the value is not null.
     *
     * @see ProductResponseDto
     * @see #productResponseDto
     */
    @JsonInclude(JsonInclude.Include.NON_NULL) // Includes the product details only if not null in the JSON response.
    @JsonProperty("product") // Maps the JSON property "product" to this field.
    private ProductResponseDto productResponseDto;
}
