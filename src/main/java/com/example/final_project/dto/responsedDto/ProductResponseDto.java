package com.example.final_project.dto.responsedDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    /**
     * Unique identifier for the product.
     */
    private Long productId;

    /**
     * Name of the product.
     */
    private String name;

    /**
     * Description of the product.
     */
    private String description;

    /**
     * Regular price of the product.
     */
    private BigDecimal price;

    /**
     * Discounted price of the product, if any.
     */
    private BigDecimal discountPrice;

    /**
     * Timestamp indicating when the product was created.
     */
    private Timestamp createdAt;

    /**
     * Timestamp indicating the last update time of the product.
     */
    private Timestamp updatedAt;

    /**
     * URL of the product's image.
     */
    private String imageURL;

    /**
     * Category details associated with the product.
     * This field will be included in the JSON response only if it is not null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("category")
    private CategoryResponseDto categoryResponseDto;
}
