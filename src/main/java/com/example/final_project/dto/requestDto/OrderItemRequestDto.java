package com.example.final_project.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDto {
    @NotNull(message = "Id is null")
    @Positive(message = "Id must be positive")
    @Min(value = 1,message = "Invalid id")
    @Max(value = 100,message = "Invalid id")
    private Long productId;

    @NotNull(message = "Quantity null")
    @Positive(message = "Quantity must be positive")
    @Min(value = 1,message = "Invalid quantity")
    @Max(value = 100,message = "Invalid quantity")
    private Integer quantity;
}
