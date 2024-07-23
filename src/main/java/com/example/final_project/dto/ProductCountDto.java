package com.example.final_project.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
public class ProductCountDto {
    /**
     * Unique identifier for the product.
     */
    private Long productId;

    /**
     * Name of the product.
     */
    private String name;

    /**
     * Number of items of the product.
     */
    private Integer count;

    /**
     * Total sum of the product.
     */
    private BigDecimal sum;

    /**
     * Constructor to initialize all fields.
     *
     * @param productId Unique identifier for the product.
     * @param name      Name of the product.
     * @param count     Number of items of the product.
     * @param sum       Total sum of the product.
     */
    public ProductCountDto(Long productId, String name, Integer count, BigDecimal sum) {
        this.productId = productId;
        this.name = name;
        this.count = count;
        this.sum = sum;
    }
}