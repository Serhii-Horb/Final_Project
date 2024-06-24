package com.example.final_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public class OrderItemDto {
    // @JsonInclude(JsonInclude.Include.NON_NULL)
//   private ProductDto product;
    @Min(value = 1,message = "Invalid value: must be at least 1")
    @Positive(message = "Invalid value: must be greater than 0")
    private int quantity;
}
