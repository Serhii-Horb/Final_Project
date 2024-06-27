package com.example.final_project.dto.requestDto;

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
    @NotBlank(message = "Invalid Id: Empty Id")
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    private Long productId;

    @NotNull(message = "Quantity null")
    @Positive(message = "Quantity must be positive")
    @Size(min = 1, max = 100, message = "Invalid Quantity: Must be of 1 - 100")
    private Integer quantity;
}
