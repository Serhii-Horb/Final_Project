package com.example.final_project.dto.responsedDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {
    /**
     * Unique identifier for the product.
     */
    private Long productId;

    /**
     * Quantity of the product ordered.
     */
    private Integer quantity;

    /**
     * Unique identifier for the order item.
     */
    private Long orderItemId;

    /**
     * Price of the product at the time of purchase.
     */
    private BigDecimal priceAtPurchase;

    /**
     * Order details of the order item.
     * This field will be included in the JSON response only if it is not null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order")
    private OrderResponseDto orderDto;
}
