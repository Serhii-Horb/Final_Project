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
public class CartResponseDto {
    /**
     * Unique identifier for the cart.
     *
     * @see #cartId
     */
    private Long cartId;

    /**
     * Details of the associated user.
     * Included in the response only if the value is not null.
     *
     * @see UserResponseDto
     * @see #userResponseDto
     */
    @JsonInclude(JsonInclude.Include.NON_NULL) // Includes the user details only if not null in the JSON response.
    @JsonProperty("user") // Maps the JSON property "user" to this field.
    private UserResponseDto userResponseDto;
}
